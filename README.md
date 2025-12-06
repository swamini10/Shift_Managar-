# 📅 Shift Manager - Web-Based Shift Management System

A modern, web-based shift management tool designed for small teams to efficiently track and manage work shifts. Built with Spring Boot and MySQL, this application provides an intuitive interface for both employees and administrators.

## ✨ Key Features

### 👤 User Management
- **Employee Registration**: Self-service registration for new employees
- **Secure Login**: Email and password authentication
- **Role-Based Access**: Separate interfaces for Admin and Employee users
- **Profile Management**: Employees can view and manage their profiles

### 📋 Shift Management
- **Shift Creation**: Admins can create and assign shifts to employees
- **Shift Types**: Support for Morning (6 AM - 2 PM), Afternoon (2 PM - 10 PM), and Night (10 PM - 6 AM) shifts
- **Schedule Viewing**: Employees can view their upcoming shifts and schedules
- **Shift Updates**: Admins can modify or cancel shifts
- **Status Tracking**: Track shift status (Scheduled, In Progress, Completed, Cancelled)

### 🔔 Notifications System
- **Real-Time Alerts**: Employees receive notifications for shift assignments, changes, and cancellations
- **Unread Count**: Badge showing number of unread notifications
- **Notification History**: View all past notifications
- **Mark as Read**: Mark notifications individually or all at once

### 💾 Database Storage
- **MySQL Database**: Persistent storage for users and shifts
- **Structured Schema**: Optimized tables with proper indexing for performance
- **Data Integrity**: Foreign keys and constraints to maintain data consistency

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.5.7
- **Language**: Java 17
- **Database**: MySQL 8.0
- **Frontend**: HTML5, CSS3, Vanilla JavaScript
- **Security**: Spring Security (Foundation for future enhancements)
- **API**: RESTful API with JSON

## 📦 Project Structure

```
Shift_Managar/
├── src/
│   ├── main/
│   │   ├── java/demo/Shift_Managar/
│   │   │   ├── controller/          # REST API endpoints
│   │   │   ├── model/               # Entity classes
│   │   │   ├── repository/          # Data access layer
│   │   │   ├── service/             # Business logic
│   │   │   └── dto/                 # Data Transfer Objects
│   │   └── resources/
│   │       ├── static/              # Frontend HTML/CSS/JS
│   │       └── application.properties
│   └── test/
├── pom.xml                          # Maven dependencies
└── database.sql                     # Database initialization script
```

## 🚀 Getting Started

### Prerequisites
- **Java 17 or higher**
- **MySQL 8.0 or higher**
- **Maven 3.6+**
- **Git**

### Installation Steps

#### 1. Clone the Repository
```bash
git clone <repository-url>
cd Shift_Managar
```

#### 2. Set Up MySQL Database
```bash
# Open MySQL client
mysql -u root -p

# Create database and tables
source database.sql
```

#### 3. Configure Database Connection
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/shift_manager?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```

#### 4. Build the Project
```bash
mvn clean package
```

#### 5. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 🎯 Usage Guide

### For Administrators

1. **Login**
   - Visit `http://localhost:8080/api/index.html`
   - Username: `admin@shiftmanager.com`
   - Password: `admin123`

2. **Create Shifts**
   - Navigate to "Create New Shift" section
   - Select an employee from dropdown
   - Choose shift type (Morning, Afternoon, Night)
   - Set start and end date/time
   - Click "Create Shift"

3. **Manage Shifts**
   - View all scheduled shifts in the table
   - Click "Edit" to modify shift details
   - Click "Delete" to remove a shift

4. **View Employees**
   - See list of all employees
   - Monitor their status

### For Employees

1. **Register**
   - Click "Register" tab on login page
   - Fill in email, full name, phone, and password
   - Account will be created as Employee role

2. **Login**
   - Use your registered email and password
   - View your dashboard with upcoming shifts

3. **View Shifts**
   - "Your Upcoming Shifts" shows shifts for next 3 months
   - "All Your Shifts" shows complete shift history
   - Each shift card displays start time, end time, and status

4. **Check Notifications**
   - Click notification bell icon
   - View all unread notifications
   - Click notification to mark as read
   - Receive alerts for shift assignments and changes

## 🔌 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/employees` - List all employees
- `GET /api/users/admins` - List all admins
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Shifts
- `POST /api/shifts` - Create new shift
- `GET /api/shifts/employee/{employeeId}` - Get employee shifts
- `GET /api/shifts/employee/{employeeId}/upcoming` - Get upcoming shifts
- `GET /api/shifts/all-scheduled` - Get all scheduled shifts
- `GET /api/shifts/{id}` - Get shift by ID
- `PUT /api/shifts/{id}` - Update shift
- `PUT /api/shifts/{id}/cancel` - Cancel shift
- `DELETE /api/shifts/{id}` - Delete shift

### Notifications
- `GET /api/notifications/user/{userId}` - Get user notifications
- `GET /api/notifications/user/{userId}/unread` - Get unread notifications
- `GET /api/notifications/user/{userId}/unread-count` - Count unread
- `PUT /api/notifications/{id}/mark-read` - Mark as read
- `PUT /api/notifications/user/{userId}/mark-all-read` - Mark all as read
- `DELETE /api/notifications/{id}` - Delete notification

## 📊 Database Schema

### Users Table
- Stores user information with role-based access
- Supports both Admin and Employee roles
- Tracks user creation and modification timestamps

### Shifts Table
- Manages shift assignments to employees
- Stores shift start/end times and type
- Tracks shift status through workflow

### Notifications Table
- Stores all notifications for users
- Tracks read/unread status
- Maintains notification history

## 🔐 Security Features

- Email and password validation
- Role-based access control (RBAC)
- Soft delete for user records
- Database constraints and foreign keys
- CORS enabled for frontend integration

## 🎨 Frontend Features

- **Responsive Design**: Works on desktop, tablet, and mobile
- **Modern UI**: Clean and intuitive interface
- **Real-time Updates**: Notifications refresh every 30 seconds
- **Error Handling**: User-friendly error messages
- **Form Validation**: Client-side validation for user inputs

## 📝 Sample Data

The database includes sample data:
- **Admin User**: admin@shiftmanager.com / admin123
- **Employee Users**: john@example.com, jane@example.com, bob@example.com (all with password: password123)

## 🚧 Future Enhancements

- [ ] Email notifications for shift changes
- [ ] SMS alerts for urgent notifications
- [ ] Shift swap/bidding functionality
- [ ] Calendar view for shifts
- [ ] Advanced reporting and analytics
- [ ] Mobile app (React Native/Flutter)
- [ ] OAuth integration
- [ ] Multi-language support
- [ ] Dark mode theme
- [ ] Attendance tracking
- [ ] Overtime management
- [ ] Leave management system

## 🐛 Troubleshooting

### Port Already in Use
If port 8080 is already in use, change it in `application.properties`:
```properties
server.port=8081
```

### Database Connection Error
Ensure MySQL is running and credentials are correct:
```bash
# Check MySQL service
mysql -u root -p -e "SELECT 1;"
```

### CORS Issues
If frontend can't connect to backend, ensure CORS is enabled (already configured in controllers).

## 📞 Support

For issues or questions:
1. Check the logs in console
2. Verify database connection
3. Ensure all ports are accessible
4. Check browser console for frontend errors

## 📄 License

This project is provided as-is for educational and commercial use.

## 👥 Contributors

- Project developed as a comprehensive shift management solution

---

**Happy Shift Managing! 🎉**
