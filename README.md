# java-course-module-2

## Port 8080 bị chiếm

### macOS

Xem process đang dùng port:

```bash
lsof -i :8080
```

Tắt process:

```bash
lsof -ti :8080 | xargs kill
```

Force kill nếu cần:

```bash
lsof -ti :8080 | xargs kill -9
```

---

### Windows

Xem process đang dùng port:

```cmd
netstat -ano | findstr :8080
```

Ví dụ:

```text
TCP    0.0.0.0:8080    0.0.0.0:0    LISTENING    12345
```

Trong đó `12345` là **PID** của process đang dùng port `8080`.

Tắt process:

```cmd
taskkill /PID 12345 /F
```

Hoặc dùng PowerShell:

```powershell
Stop-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess -Force
```

---

## Đổi port Spring Boot

Nếu không muốn tắt process đang dùng port `8080`, có thể đổi port của Spring Boot.

Trong `application.properties`:

```properties
server.port=8081
```

Sau đó chạy lại ứng dụng và truy cập:

```text
http://localhost:8081
```
