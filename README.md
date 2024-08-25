Hướng dẫn cấu hình.</br>

1. Thông tin phần mềm được sử dụng backend:</br>
   _ Java 1.8: jdk-8u251-windows-x64 </br>
   _ Database: SQL Server 2019 </br>
   _ Docker: https://www.docker.com/products/docker-desktop/ <br/>
   _ Postman: https://www.postman.com/downloads/ <br/>

2. Cài đặt và cấu hình: </br>
   2.1 Docker: </br>
   _ Cài đặt: https://docs.docker.com/desktop/install/windows-install/ </br>
   _ Cấu hình: https://docs.docker.com/desktop/wsl/ </br>
   _ Nhập "Turn Windows features on or off" vào ô Search trong Windows </br>
   _ Chọn vào ô Container và Hyper-V </br>
   ![img_1.png](img_1.png) </br>
3. Host backend lên Docker </br>
   _ Tạo image cho backend: "docker build --tag hb-payment-processing-image ."<br/>
   ![img_2.png](img_2.png) <br/>
   _ Kéo image của SQL Server 2019 đã có sẵn về máy: "docker pull mcr.microsoft.com/mssql/server:2019-latest" <br/>
   ![img_3.png](img_3.png) <br/>
   _ Sử dụng docker compose để tạo 1 container cho backend và 1 container cho MS SQL Server 2019 <br/>
   "docker compose up -d" <br/>
   ![img_4.png](img_4.png) <br/>
4. Remote vào container của SQL Server 2019 để tạo database cho website <br/>
   _ Mở cmd, và gõ "ipconfig /all" <br/>
   _ Tìm đến dòng "IPv4 Address" bên trong mục "Ethernet adapter vEthernet (WSL):" <br/>
   _ Mở Microsoft SQL Server Management Studio <br/>
   _ nhập địa chỉ IP vừa tìm được cùng với port của database trong file "compose.yaml" vào ô "Server name" theo format "
   xxx.xx.xxx.x,port" <br/>
   _ Nhập tên và mật khẩu đăng nhập được ghi sẵn trong file "application.properties" </br>
   _ Sử dụng và chạy script có đuôi ".sql" có sẵn trong repository để tạo database </br>
5. Sử dụng Postman: </br>
   5.1. Chức năng đăng nhập: </br>
   _ Sử dụng ip và port đã cấu hình sẵn trong file "compose.yaml" </br>
   _  "127.0.0.1:8081/guest/login/" và đặt {"email":"ducminh1@gmail.com", "pwd":"123a@$45A6789"} trong mục "Body" -> "
   Raw" -> "JSON"</br>
   ![img_7.png](img_7.png) </br>
   5.2. Chức năng thanh toán: </br>
   _ Sử dụng token trong được trả về sau khi đăng nhập và nhập vào mục "Authorization" -> "Bearer token" type <br/>
   _ Sử dụng ip và port đã cấu hình sẵn trong file "compose.yaml" </br>
   _ "127.0.0.1:8081/user/invoice-payment/" và đặt các giá trị nào vào mục "Params" -> "Query Params" </br>
   _ "invoiceCode": "ABHG15662", "total": "2900000", "description": "Duc chuyen tien lan 1", "tagName": "Bills", "
   senderAccountNumberCode": "INVABC123457", "receiverAccountNumberCode":"INVABC123433" <br/>
   _ "senderAccountNumberCode" và "tagName" được giả lập trong database <br/> 
   ![img_8.png](img_8.png)
    
    
    
    
    
   