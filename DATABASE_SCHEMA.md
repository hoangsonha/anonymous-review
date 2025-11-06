# Database Schema - Caregiving Service

## Overview
Hệ thống quản lý đặt lịch chăm sóc người già với thanh toán và quản lý công việc.

## Tables

### 1. users
Quản lý thông tin người dùng (cả seeker và caregiver)

```sql
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    user_type VARCHAR(20) NOT NULL, -- 'SEEKER' hoặc 'CAREGIVER'
    avatar_url TEXT,
    bank_account_number VARCHAR(50), -- Cho caregiver nhận tiền
    bank_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 2. bookings
Thông tin đặt lịch từ seeker đến caregiver

```sql
CREATE TABLE bookings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    seeker_id INTEGER NOT NULL,
    caregiver_id INTEGER NOT NULL,
    
    -- Thông tin dịch vụ
    service_type VARCHAR(100) NOT NULL, -- VD: 'daily_care', 'medical_care', etc.
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    start_time TIME NOT NULL,
    duration_hours INTEGER NOT NULL,
    
    -- Thông tin bệnh nhân/người cần chăm sóc
    patient_name VARCHAR(255) NOT NULL,
    patient_age INTEGER,
    patient_condition TEXT, -- Tình trạng sức khỏe
    special_requirements TEXT, -- Yêu cầu đặc biệt
    address TEXT NOT NULL,
    
    -- Trạng thái
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING_PAYMENT', 
    -- 'PENDING_PAYMENT' -> 'PAID' -> 'PENDING_ACCEPTANCE' -> 'ACCEPTED' -> 'IN_PROGRESS' -> 'COMPLETED' -> 'CANCELLED'
    
    -- Ghi chú
    note TEXT,
    cancellation_reason TEXT,
    cancelled_by INTEGER, -- user_id of who cancelled
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (seeker_id) REFERENCES users(id),
    FOREIGN KEY (caregiver_id) REFERENCES users(id),
    FOREIGN KEY (cancelled_by) REFERENCES users(id)
);
```

### 3. payments
Quản lý thanh toán và giao dịch

```sql
CREATE TABLE payments (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    booking_id INTEGER NOT NULL UNIQUE, -- Mỗi booking có 1 payment
    
    -- Thông tin thanh toán
    amount DECIMAL(10, 2) NOT NULL, -- Tổng tiền seeker trả
    platform_fee DECIMAL(10, 2) NOT NULL, -- Phí hệ thống
    caregiver_amount DECIMAL(10, 2) NOT NULL, -- Tiền caregiver nhận (amount - platform_fee)
    
    -- Trạng thái thanh toán
    payment_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    -- 'PENDING' -> 'PAID' -> 'HELD' -> 'RELEASED' -> 'REFUNDED'
    
    -- Thông tin giao dịch
    payment_method VARCHAR(50), -- 'card', 'bank_transfer', 'e_wallet'
    transaction_id VARCHAR(255), -- ID từ payment gateway
    payment_gateway VARCHAR(50), -- VD: 'stripe', 'vnpay', 'momo'
    
    -- Timestamps cho từng trạng thái
    paid_at TIMESTAMP, -- Khi seeker thanh toán
    held_at TIMESTAMP, -- Khi hệ thống giữ tiền
    released_at TIMESTAMP, -- Khi chuyển tiền cho caregiver
    refunded_at TIMESTAMP, -- Nếu hoàn tiền
    
    refund_reason TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);
```

### 4. work_schedules
Lịch làm việc được tạo khi caregiver chấp nhận booking

```sql
CREATE TABLE work_schedules (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    booking_id INTEGER NOT NULL UNIQUE, -- 1 booking tạo 1 work_schedule
    caregiver_id INTEGER NOT NULL,
    seeker_id INTEGER NOT NULL,
    
    -- Thông tin lịch trình
    schedule_name VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    
    -- Trạng thái
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    -- 'ACTIVE' -> 'COMPLETED' -> 'CANCELLED'
    
    total_tasks INTEGER DEFAULT 0, -- Số task đã tạo
    completed_tasks INTEGER DEFAULT 0, -- Số task đã hoàn thành
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP,
    
    FOREIGN KEY (booking_id) REFERENCES bookings(id),
    FOREIGN KEY (caregiver_id) REFERENCES users(id),
    FOREIGN KEY (seeker_id) REFERENCES users(id)
);
```

### 5. work_tasks
Các công việc cụ thể trong work_schedule

```sql
CREATE TABLE work_tasks (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    work_schedule_id INTEGER NOT NULL,
    
    -- Thông tin task
    task_name VARCHAR(255) NOT NULL,
    task_description TEXT,
    task_type VARCHAR(50), -- VD: 'medication', 'feeding', 'bathing', 'exercise'
    
    -- Thời gian
    scheduled_date DATE NOT NULL,
    scheduled_time TIME,
    estimated_duration INTEGER, -- phút
    
    -- Trạng thái
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    -- 'PENDING' -> 'IN_PROGRESS' -> 'COMPLETED' -> 'SKIPPED'
    
    completed_at TIMESTAMP,
    completion_note TEXT,
    
    -- Thứ tự ưu tiên
    priority INTEGER DEFAULT 0, -- 0: normal, 1: high, 2: urgent
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (work_schedule_id) REFERENCES work_schedules(id)
);
```

### 6. work_notes
Ghi chú trong quá trình làm việc

```sql
CREATE TABLE work_notes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    work_schedule_id INTEGER NOT NULL,
    
    -- Nội dung ghi chú
    note_title VARCHAR(255),
    note_content TEXT NOT NULL,
    note_type VARCHAR(50), -- 'general', 'health_update', 'incident', 'observation'
    
    -- Người tạo (thường là caregiver)
    created_by INTEGER NOT NULL,
    
    -- Đính kèm
    has_attachment BOOLEAN DEFAULT FALSE,
    attachment_url TEXT, -- Ảnh hoặc file đính kèm
    
    -- Quan trọng?
    is_important BOOLEAN DEFAULT FALSE,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (work_schedule_id) REFERENCES work_schedules(id),
    FOREIGN KEY (created_by) REFERENCES users(id)
);
```

### 7. reviews (Optional - Đánh giá sau khi hoàn thành)
```sql
CREATE TABLE reviews (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    booking_id INTEGER NOT NULL UNIQUE,
    work_schedule_id INTEGER NOT NULL,
    
    -- Người đánh giá và người được đánh giá
    reviewer_id INTEGER NOT NULL, -- Thường là seeker
    reviewed_user_id INTEGER NOT NULL, -- Thường là caregiver
    
    -- Đánh giá
    rating INTEGER NOT NULL CHECK(rating >= 1 AND rating <= 5),
    review_text TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (booking_id) REFERENCES bookings(id),
    FOREIGN KEY (work_schedule_id) REFERENCES work_schedules(id),
    FOREIGN KEY (reviewer_id) REFERENCES users(id),
    FOREIGN KEY (reviewed_user_id) REFERENCES users(id)
);
```

## Flow Logic

### 1. Booking Flow
```
1. Seeker tạo booking (status = 'PENDING_PAYMENT')
2. Seeker thanh toán → Payment record được tạo
   - payment_status: 'PENDING' → 'PAID'
   - Booking status: 'PENDING_PAYMENT' → 'PAID'
3. Sau khi payment thành công → Booking status: 'PAID' → 'PENDING_ACCEPTANCE'
   (Booking được gửi đến caregiver)
4. Caregiver chấp nhận:
   - Booking status: 'PENDING_ACCEPTANCE' → 'ACCEPTED'
   - Tạo work_schedule
   - Payment status: 'PAID' → 'HELD' (hệ thống giữ tiền)
5. Bắt đầu làm việc:
   - Booking status: 'ACCEPTED' → 'IN_PROGRESS'
   - Work_schedule status: 'ACTIVE'
   - Tạo các work_tasks
   - Tạo work_notes theo tiến trình
6. Hoàn thành:
   - Booking status: 'IN_PROGRESS' → 'COMPLETED'
   - Work_schedule status: 'ACTIVE' → 'COMPLETED'
   - Payment status: 'HELD' → 'RELEASED' (chuyển tiền cho caregiver)
```

### 2. Payment Flow
```
SEEKER PAYS:
payment_status: 'PENDING' → 'PAID'
↓
CAREGIVER ACCEPTS:
payment_status: 'PAID' → 'HELD' (hệ thống giữ tiền)
↓
WORK COMPLETED:
payment_status: 'HELD' → 'RELEASED' (chuyển tiền cho caregiver)

CANCELLATION:
payment_status → 'REFUNDED' (hoàn tiền cho seeker)
```

## Indexes (Để tối ưu performance)

```sql
-- Bookings
CREATE INDEX idx_bookings_seeker ON bookings(seeker_id);
CREATE INDEX idx_bookings_caregiver ON bookings(caregiver_id);
CREATE INDEX idx_bookings_status ON bookings(status);
CREATE INDEX idx_bookings_dates ON bookings(start_date, end_date);

-- Payments
CREATE INDEX idx_payments_booking ON payments(booking_id);
CREATE INDEX idx_payments_status ON payments(payment_status);

-- Work schedules
CREATE INDEX idx_work_schedules_booking ON work_schedules(booking_id);
CREATE INDEX idx_work_schedules_caregiver ON work_schedules(caregiver_id);

-- Work tasks
CREATE INDEX idx_work_tasks_schedule ON work_tasks(work_schedule_id);
CREATE INDEX idx_work_tasks_date ON work_tasks(scheduled_date);
CREATE INDEX idx_work_tasks_status ON work_tasks(status);

-- Work notes
CREATE INDEX idx_work_notes_schedule ON work_notes(work_schedule_id);
CREATE INDEX idx_work_notes_created_by ON work_notes(created_by);
```

## Notes

1. **Payment Gateway Integration**: Bạn cần tích hợp với payment gateway như VNPay, Momo, hoặc Stripe để xử lý thanh toán.

2. **Money Holding**: Vì hệ thống không có wallet, bạn cần:
   - Sử dụng payment gateway hỗ trợ "hold" tiền (như Stripe Connect)
   - Hoặc giữ tiền trong tài khoản hệ thống và chuyển khoản sau

3. **Notifications**: Nên có thêm bảng `notifications` để thông báo cho user về các sự kiện.

4. **Transaction Logs**: Có thể thêm bảng `transaction_logs` để audit trail.

5. **Caregiver Profile**: Có thể mở rộng thêm bảng `caregiver_profiles` để lưu thông tin chuyên môn, kinh nghiệm, certifications.
