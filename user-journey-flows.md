# CÁC FLOW DIAGRAM CHI TIẾT - HỆ THỐNG CHĂM SÓC NGƯỜI CAO TUỔI

## FLOW 1: CARE SEEKER - TÌM VÀ THUÊ CAREGIVER

```mermaid
flowchart TD
    A[Mở App] --> B[Đăng ký tài khoản]
    B --> C[Xác thực OTP]
    C --> D[Tạo hồ sơ người cao tuổi]
    D --> E[Nhập thông tin sức khỏe]
    E --> F[Thiết lập nhu cầu chăm sóc]
    F --> G[AI phân tích và gợi ý]
    G --> H[Hiển thị danh sách caregiver]
    H --> I{Chọn caregiver}
    I --> J[Xem chi tiết hồ sơ]
    J --> K{Muốn tư vấn video?}
    K -->|Có| L[Đặt lịch tư vấn]
    K -->|Không| M[Đặt lịch trực tiếp]
    L --> N[Video call tư vấn]
    N --> O{Hài lòng?}
    O -->|Có| M
    O -->|Không| H
    M --> P[Chọn thời gian dịch vụ]
    P --> Q[Xác nhận booking]
    Q --> R[Thanh toán]
    R --> S[Chờ caregiver xác nhận]
    S --> T{Caregiver phản hồi}
    T -->|Chấp nhận| U[Bắt đầu dịch vụ]
    T -->|Từ chối| V[Gợi ý caregiver khác]
    V --> H
    U --> W[Theo dõi qua dashboard]
    W --> X[Nhận báo cáo real-time]
    X --> Y[Dịch vụ hoàn thành]
    Y --> Z[Đánh giá caregiver]
    Z --> AA[Hoàn thành]
```

## FLOW 2: CAREGIVER - ĐĂNG KÝ VÀ NHẬN VIỆC

```mermaid
flowchart TD
    A[Tải App Caregiver] --> B[Đăng ký tài khoản]
    B --> C[Nhập thông tin cá nhân]
    C --> D[Upload giấy tờ tùy thân]
    D --> E[Upload bằng cấp/chứng chỉ]
    E --> F[Tạo hồ sơ chuyên môn]
    F --> G[Chờ admin xác thực]
    G --> H{Admin duyệt hồ sơ}
    H -->|Từ chối| I[Thông báo lý do từ chối]
    H -->|Duyệt| J[Tài khoản được kích hoạt]
    I --> K[Chỉnh sửa hồ sơ]
    K --> G
    J --> L[Thiết lập lịch trống]
    L --> M[Chờ nhận yêu cầu]
    M --> N[Nhận thông báo booking mới]
    N --> O[Xem chi tiết yêu cầu]
    O --> P{Chấp nhận yêu cầu?}
    P -->|Từ chối| Q[Gửi lý do từ chối]
    P -->|Chấp nhận| R[Xác nhận booking]
    Q --> M
    R --> S[Chuẩn bị cho buổi làm việc]
    S --> T[Check-in khi bắt đầu]
    T --> U[Thực hiện các task chăm sóc]
    U --> V[Báo cáo tiến độ real-time]
    V --> W[Chụp ảnh minh chứng]
    W --> X[Check-out khi hoàn thành]
    X --> Y[Tạo báo cáo tổng kết]
    Y --> Z[Gửi báo cáo cho gia đình]
    Z --> AA[Nhận đánh giá từ khách hàng]
    AA --> BB[Nhận thanh toán]
    BB --> CC[Hoàn thành]
```

## FLOW 3: ADMIN - QUẢN LÝ VÀ GIÁM SÁT

```mermaid
flowchart TD
    A[Đăng nhập Admin Portal] --> B[Dashboard tổng quan]
    B --> C{Chọn chức năng}
    C -->|Xác thực user| D[Danh sách chờ duyệt]
    C -->|Giám sát chất lượng| E[Dashboard monitoring]
    C -->|Xử lý khiếu nại| F[Hệ thống dispute]
    C -->|Phân tích dữ liệu| G[Analytics & Reports]
    
    D --> H[Xem hồ sơ caregiver mới]
    H --> I[Kiểm tra giấy tờ]
    I --> J[Xác thực thông tin]
    J --> K[Phỏng vấn video]
    K --> L{Quyết định duyệt}
    L -->|Duyệt| M[Kích hoạt tài khoản]
    L -->|Từ chối| N[Gửi thông báo từ chối]
    
    E --> O[Xem metrics real-time]
    O --> P[Phát hiện vấn đề chất lượng]
    P --> Q[Cảnh báo caregiver]
    Q --> R[Theo dõi cải thiện]
    
    F --> S[Nhận khiếu nại mới]
    S --> T[Phân loại khiếu nại]
    T --> U[Thu thập bằng chứng]
    U --> V[Liên hệ các bên]
    V --> W[Đưa ra phán quyết]
    W --> X[Thực hiện biện pháp]
    
    G --> Y[Tạo báo cáo định kỳ]
    Y --> Z[Phân tích xu hướng]
    Z --> AA[Đề xuất cải thiện]
```

## FLOW 4: AI MATCHING SYSTEM

```mermaid
flowchart TD
    A[Care Seeker tạo yêu cầu] --> B[AI nhận input]
    B --> C[Phân tích hồ sơ người cao tuổi]
    C --> D[Trích xuất yêu cầu chính]
    D --> E[Lọc caregiver cơ bản]
    E --> F{Có caregiver phù hợp?}
    F -->|Không| G[Mở rộng tiêu chí tìm kiếm]
    G --> E
    F -->|Có| H[Tính điểm compatibility]
    H --> I[Áp dụng machine learning model]
    I --> J[Collaborative filtering]
    J --> K[Content-based filtering]
    K --> L[Kết hợp kết quả]
    L --> M[Sắp xếp theo điểm số]
    M --> N[Lọc top 10 caregiver]
    N --> O[Hiển thị kết quả]
    O --> P[User chọn caregiver]
    P --> Q[Thu thập feedback]
    Q --> R[Cập nhật model]
    R --> S[Cải thiện độ chính xác]
```

## FLOW 5: EMERGENCY ALERT SYSTEM

```mermaid
flowchart TD
    A{Phát hiện tình huống khẩn cấp} --> B[Người cao tuổi bấm nút SOS]
    A --> C[Caregiver báo cáo khẩn cấp]
    A --> D[Hệ thống phát hiện bất thường]
    
    B --> E[Thiết bị gửi tín hiệu]
    C --> F[Caregiver chọn loại sự cố]
    D --> G[AI phân tích dữ liệu]
    
    E --> H[Hệ thống nhận tín hiệu SOS]
    F --> I[Caregiver gửi báo cáo + ảnh/video]
    G --> J[Cảnh báo tự động]
    
    H --> K[Xác định vị trí GPS]
    I --> K
    J --> K
    
    K --> L[Gửi thông báo khẩn cấp]
    L --> M[Thông báo đến gia đình]
    L --> N[Thông báo đến caregiver]
    L --> O[Thông báo đến y tế]
    
    M --> P[Gia đình nhận SMS + Push]
    N --> Q[Caregiver gần nhất nhận cảnh báo]
    O --> R[Trung tâm y tế nhận thông tin]
    
    P --> S[Gia đình liên hệ ngay]
    Q --> T[Caregiver đến hỗ trợ]
    R --> U[Y tế cử xe cấp cứu]
    
    S --> V[Phối hợp xử lý sự cố]
    T --> V
    U --> V
    
    V --> W[Báo cáo kết quả]
    W --> X[Cập nhật hồ sơ sức khỏe]
    X --> Y[Rút kinh nghiệm cải thiện]
```

## FLOW 6: PAYMENT PROCESSING

```mermaid
flowchart TD
    A[Dịch vụ hoàn thành] --> B[Care seeker xác nhận]
    B --> C[Hệ thống tính toán chi phí]
    C --> D[Hiển thị hóa đơn chi tiết]
    D --> E{Care seeker đồng ý?}
    E -->|Không| F[Khiếu nại về chi phí]
    E -->|Có| G[Chọn phương thức thanh toán]
    F --> H[Admin xử lý khiếu nại]
    H --> I[Điều chỉnh hóa đơn]
    I --> G
    G --> J{Loại thanh toán}
    J -->|Thẻ tín dụng| K[Nhập thông tin thẻ]
    J -->|Ví điện tử| L[Chọn ví MoMo/ZaloPay]
    J -->|Chuyển khoản| M[Thông tin tài khoản]
    K --> N[Xác thực 3D-Secure]
    L --> O[Đăng nhập ví điện tử]
    M --> P[Xác nhận chuyển khoản]
    N --> Q[Xử lý thanh toán]
    O --> Q
    P --> Q
    Q --> R{Thanh toán thành công?}
    R -->|Thất bại| S[Thông báo lỗi]
    R -->|Thành công| T[Tiền vào escrow]
    S --> G
    T --> U[Thông báo cho caregiver]
    U --> V[Caregiver xác nhận nhận tiền]
    V --> W[Chuyển tiền từ escrow]
    W --> X[Caregiver nhận thanh toán]
    X --> Y[Xuất hóa đơn điện tử]
    Y --> Z[Hoàn tất giao dịch]
```

## FLOW 7: QUALITY MONITORING

```mermaid
flowchart TD
    A[Dịch vụ đang diễn ra] --> B[Thu thập dữ liệu real-time]
    B --> C[Caregiver báo cáo tiến độ]
    B --> D[Care seeker theo dõi]
    B --> E[Hệ thống ghi log hoạt động]
    
    C --> F[Cập nhật dashboard]
    D --> G[Phản hồi từ gia đình]
    E --> H[Phân tích hành vi]
    
    F --> I[AI phân tích chất lượng]
    G --> I
    H --> I
    
    I --> J{Phát hiện vấn đề?}
    J -->|Không| K[Tiếp tục giám sát]
    J -->|Có| L[Phân loại mức độ nghiêm trọng]
    
    L --> M{Mức độ nghiêm trọng}
    M -->|Thấp| N[Gửi nhắc nhở caregiver]
    M -->|Trung bình| O[Cảnh báo admin]
    M -->|Cao| P[Dừng dịch vụ ngay]
    
    N --> Q[Caregiver cải thiện]
    O --> R[Admin can thiệp]
    P --> S[Thông báo khẩn cấp]
    
    Q --> T[Theo dõi cải thiện]
    R --> U[Hỗ trợ giải quyết]
    S --> V[Tìm caregiver thay thế]
    
    T --> W[Cập nhật điểm chất lượng]
    U --> W
    V --> W
    
    W --> X[Báo cáo định kỳ]
    X --> Y[Phân tích xu hướng]
    Y --> Z[Cải thiện hệ thống]
```

## FLOW 8: COMMUNICATION SYSTEM

```mermaid
flowchart TD
    A[Bắt đầu dịch vụ] --> B[Tạo nhóm chat]
    B --> C[Thêm thành viên]
    C --> D[Gia đình + Caregiver]
    D --> E{Loại tin nhắn}
    
    E -->|Văn bản| F[Gửi tin nhắn chat]
    E -->|Ảnh/Video| G[Upload media]
    E -->|Ghi âm| H[Gửi voice message]
    E -->|Video call| I[Bắt đầu cuộc gọi]
    E -->|Báo cáo| J[Gửi báo cáo structured]
    
    F --> K[Tin nhắn được mã hóa]
    G --> L[Nén và upload file]
    H --> M[Chuyển đổi voice-to-text]
    I --> N[Thiết lập video call]
    J --> O[Template báo cáo]
    
    K --> P[Gửi đến tất cả thành viên]
    L --> P
    M --> P
    N --> Q[Cuộc gọi video]
    O --> P
    
    P --> R[Push notification]
    Q --> S[Ghi lại cuộc gọi]
    
    R --> T[Thành viên nhận thông báo]
    S --> U[Lưu trữ recording]
    
    T --> V[Đọc và phản hồi]
    U --> W[Chia sẻ với gia đình]
    
    V --> X{Cần phản hồi khẩn cấp?}
    W --> Y[Lưu vào hồ sơ]
    
    X -->|Có| Z[Chuyển sang emergency flow]
    X -->|Không| AA[Tiếp tục chat bình thường]
    
    Y --> BB[Cập nhật timeline chăm sóc]
```

Các flow diagram này mô tả chi tiết cách thức hoạt động của từng tính năng trong hệ thống, giúp bạn hiểu rõ quy trình và luồng xử lý của ứng dụng chăm sóc người cao tuổi.