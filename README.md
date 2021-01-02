- Dockerfile: đem về build rồi chạy, nhớ mapping port 8080 của container với port của máy ngoài  
- tài khoản admin: username/password: admin/admin  
- trừ Get order history, các phương thức get khác không cần token  
- API document ở URI: /swagger-ui.html  
## Build dockerfile:
- Mở terminal/CMD trong thư mục project (chứa file Dockerfile)
 gõ lệnh: docker build . (có dấu chấm đàng sau)
- Khi chạy xong màng hình sẽ hiện Successfully built: <imageId> (giả sử là 3d5840b14b88)
- gõ lệnh:   docker run -dp "8080:8080" 3d5840b14b88
- Backend API sẽ chạy ở port 8080 lúc này
