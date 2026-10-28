Mục lục

Bước 1: Tạo database theo đề:3

Bước 2: Tạo service đầu tiên theo đề: StudentID.ServiceName3

Bước 3: Cấu hình properties7

Bước 4: Tạo các packages7

Bước 5: Tạo entities8

Bước 6: Tạo DTO11

Tạo lớp GlobalExceptionHandler.java11

ApiResponse: dto/ApiResponse.java11

Lớp UserDTO: dto/UserDTO.java (validate theo ràng buộc DB)12

Tạo PageDTO: dto/PageDTO.java14

Bước 7: Tạo repository: repository/UserRepository.java14

Bước 8: Tạo interface UserService: service/UserService.java (interface)15

Bước 9: service/impl/UserServiceImpl.java16

Bước 10: Tạo UserController: controller/SE123456UserController.java22

Bước 11: config/CorsConfig.java24

Bước 12: config/OpenApiConfig.java26

Checklist kiểm tra UserService27

Phần 2: Tạo PostService (port 8082) — dùng OpenFeign gọi UserService32

Bước 1: Tạo project PostService33

Bước 2: Cấu hình properties38

Bước 3: Cấu hình project38

Bước 4: Main class – bật @EnableFeignClients38

Bước 5: Tạo Entity39

Tạo PostStatus enum39

Tạo Entity Post: entity/Post.java39

Map với bảng posts trong database39

Tạo các lớp common40

PostConstants.java: common/PostConstants.java40

Bước 6: Lớp DTO41

Tạo lớp ApiResponse41

Tạo PageDTO:43

Tạo lớp PostDTO45

Tạo UserDTO: dto/UserDTO.java (bản sao cục bộ, nhận dữ liệu qua Feign)47

Tạo lớp PostDetailDTO49

Tạo lớp:  common/GlobalExceptionHandler.java51

Bước 7: Tạo Repository52

Bước 8: Cấu hình OpenFeign: client/UserClient.java — @FeignClient gọi UserService53

Bước 9: config/HttpClientConfig.java (bean ObjectMapper để convert Object data)56

Bước 10: config/CorsConfig.java58

Bước 11: config/OpenApiConfig.java60

Chạy ứng dụng PostService61

Chạy swagger để kiểm tra:61

Bước 12: Tạo service: service/PostService.java (interface)61

Bước 13: Tạo PostServiceImpl: service/impl/PostServiceImpl.java — logic + xử lý 404 của Feign62

Bước 14: Tạo controller: controller/SE123456PostController.java71

Checklist Bước 2 (PostService)73

Phần 3: MediaGateway (port 8080)80

Bước 1: Tạo mới project80

Bước 2: Cấu hình properties84

Bước 3: config/CorsConfig.java — dùng CorsWebFilter (WebFlux), không WebMvcConfigurer85

Checklist Bước 3 (MediaGateway)87

Checklist nộp bài87

1.Với mỗi project (UserService, PostService, MediaGateway): chạy mvn clean để xóa thư mục target/. (rất quan trọng vì thư mục này có thể lớn hơn 40MB so với kích thước nộp file trên PEA)88

2.Chuột phải folder project Code PE chứa 3 project trên→ Send to → Compressed (zipped) folder.88

3.Nộp file nén trên lên PEA.88

Chúc các em chuẩn bị bài, thi đạt kết quả tốt , cô bichtra!88

Bước 1: Tạo database theo đề:

CREATE DATABASE MSS301_2026_PE_MEDIA;

GO

USE MSS301_2026_PE_MEDIA;

GO

New Query, copy nội dung ở 2 files .txt:

Chạy `create_users_sql.txt` → `create_posts_sql.txt`.

Dữ liệu Seed:

INSERT INTO users (username, email, role, rate_per_post, max_daily_posts, level, status) VALUES

('creator_a','a@example.com','CREATOR',500000,3,1,'ACTIVE'),

('creator_b','b@example.com','CREATOR',300000,1,1,'ACTIVE'),

('creator_c','c@example.com','CREATOR',800000,4,2,'SUSPENDED');

✅ Checklist:** DB tạo xong, 2 bảng đúng ràng buộc, có ≥2 user ACTIVE.

Bước 2: Tạo service đầu tiên theo đề: StudentID.ServiceName

TODO 1 — UserService (8081)

Lưu ý: Cấu hình project:

Project name: <studentID>ServiceName

Location: Duyệt đường dẫn lưu project ở thư mục Code_PE

Package: fu.StudentID.ServiceName

Maven

JDK: 21

Group: fu.studentID

Artifact: userservice

Các dependencies cần:

Dependencies (pom.xml)

`spring-boot-starter-web`, `-data-jpa`, `-validation`, `mssql-jdbc`(runtime), `springdoc-openapi-starter-webmvc-ui:3.0.3`, `-test`.

Cấu hình file pom.xml như sau:

<properties>    <java.version>21</java.version></properties><dependencies>    <dependency>        <groupId>org.springframework.boot</groupId>        <artifactId>spring-boot-starter-data-jpa</artifactId>    </dependency>    <dependency>        <groupId>org.springframework.boot</groupId>        <artifactId>spring-boot-starter-validation</artifactId>    </dependency>    <dependency>        <groupId>org.springframework.boot</groupId>        <artifactId>spring-boot-starter-webmvc</artifactId>    </dependency>    <dependency>        <groupId>com.microsoft.sqlserver</groupId>        <artifactId>mssql-jdbc</artifactId>        <scope>runtime</scope>    </dependency>    <dependency>        <groupId>org.projectlombok</groupId>        <artifactId>lombok</artifactId>        <optional>true</optional>    </dependency>    <dependency>        <groupId>org.springframework.boot</groupId>        <artifactId>spring-boot-starter-data-jpa-test</artifactId>        <scope>test</scope>    </dependency>    <dependency>        <groupId>org.springframework.boot</groupId>        <artifactId>spring-boot-starter-validation-test</artifactId>        <scope>test</scope>    </dependency>    <dependency>        <groupId>org.springframework.boot</groupId>        <artifactId>spring-boot-starter-webmvc-test</artifactId>        <scope>test</scope>    </dependency>    <dependency>        <groupId>org.springdoc</groupId>        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>        <version>3.0.3</version>    </dependency>    <dependency>        <groupId>org.springframework.boot</groupId>        <artifactId>spring-boot-starter-test</artifactId>        <scope>test</scope>    </dependency></dependencies>

Load Maven

Bước 3: Cấu hình properties

spring.application.name=SE123456UserServiceserver.port= 8081spring.datasource.url= jdbc:sqlserver://localhost:1433;databaseName=MSS301_2026_PE_MEDIA; encrypt = false;spring.datasource.username= saspring.datasource.password= saspring.datasource.driver-class-name= com.microsoft.sqlserver.jdbc.SQLServerDriverspring.jpa.hibernate.ddl-auto= nonespring.jpa.show-sql=truespringdoc.swagger-ui.path=/swagger-ui.htmlspringdoc.api-docs.path=/v3/api-docs

Bước 4: Tạo các packages

fu.se123456.userservice → entity, repository, service, service/impl, controller, dto, config, common.

Cấu trúc project:

Bước 5: Tạo entities

Tạo entity User (map với bảng users): lưu ý về tên cột trong bảng đã cho sẵn, kiểu dữ liệu, các ràng buộc unique, length,…

Tạo enum Role:

/** Vai tro cua user. Luu vao DB duoi dang chuoi (nvarchar) qua @Enumerated(EnumType.STRING). */

Enum UserStatus

Thêm getter, setter (Alt + Insert)

Chạy ứng dụng để kiểm tra:OK với kết quả bên dưới, đã chạy cổng 8081

Bước 6: Tạo DTO

Tạo lớp UserConstants chứa các hằng lưu giá trị chuỗi, trong thư mục common

Tạo lớp GlobalExceptionHandler.java

để mọi exception bất ngờ sau này trả đúng format ApiResponse{status:0} + HTTP 500 thay vì Whitelabel Error.

ApiResponse: dto/ApiResponse.java

Chú ý về kiểu dữ liệu của các thuộc tính của lớp ApiResponse:

Lớp UserDTO: dto/UserDTO.java (validate theo ràng buộc DB)

Tạo PageDTO: dto/PageDTO.java

Bước 7: Tạo repository: repository/UserRepository.java

package fu.se123456.userservice.repository;

import fu.se123456.userservice.entity.Role;

import fu.se123456.userservice.entity.User;

import fu.se123456.userservice.entity.UserStatus;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

boolean existsByUsername(String username);

boolean existsByUsernameAndIdNot(String username, Long id);

// Null-safe filter: when a param is null it is ignored

@Query("""

SELECT u FROM User u

WHERE (:role   IS NULL OR u.role   = :role)

AND (:status IS NULL OR u.status = :status)

""")

Page<User> search(@Param("role") Role role,

@Param("status") UserStatus status,

Pageable pageable);

}

Bước 8: Tạo interface UserService: service/UserService.java (interface)

Bước 9: service/impl/UserServiceImpl.java

Lưu ý về các mã Http code status, message trả về, các endpoints đúng

package fu.se123456.userservice.service.impl;import fu.se123456.userservice.dto.ApiResponse;import fu.se123456.userservice.dto.PageDTO;import fu.se123456.userservice.dto.UserDTO;import fu.se123456.userservice.entity.Role;import fu.se123456.userservice.entity.User;import fu.se123456.userservice.entity.UserStatus;import fu.se123456.userservice.repository.UserRepository;import fu.se123456.userservice.service.UserService;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import java.util.List;@Servicepublic class UserServiceImpl implements UserService {    private final UserRepository userRepository;    public UserServiceImpl(UserRepository userRepository) {        this.userRepository = userRepository;    }    @Override    @Transactional    public ApiResponse createUser(UserDTO dto) {        // status = 3 -> duplicated username        if (userRepository.existsByUsername(dto.getUsername())) {            return ApiResponse.error(3, "Username is duplicated");        }        User user = new User();        user.setUsername(dto.getUsername());        user.setEmail(dto.getEmail());        user.setRole(Role.valueOf(dto.getRole()));        user.setRatePerPost(dto.getRatePerPost());        user.setMaxDailyPosts(dto.getMaxDailyPosts());        user.setLevel(dto.getLevel());        // Default status is ACTIVE when not provided        user.setStatus(dto.getStatus() == null ? UserStatus.ACTIVE : UserStatus.valueOf(dto.getStatus()));        User saved = userRepository.save(user);        return ApiResponse.ok("User created successfully", toDTO(saved));    }    @Override    @Transactional    public ApiResponse updateUser(Long userId, UserDTO dto) {        User user = userRepository.findById(userId).orElse(null);        if (user == null) {            return ApiResponse.error(4, "User is not found");        }        // Duplicated username (excluding current user)        if (dto.getUsername() != null                && userRepository.existsByUsernameAndIdNot(dto.getUsername(), userId)) {            return ApiResponse.error(3, "Username is duplicated");        }        // Partial update: only non-null fields        if (dto.getUsername() != null)      user.setUsername(dto.getUsername());        if (dto.getEmail() != null)         user.setEmail(dto.getEmail());        if (dto.getRole() != null)          user.setRole(Role.valueOf(dto.getRole()));        if (dto.getRatePerPost() != null)   user.setRatePerPost(dto.getRatePerPost());        if (dto.getMaxDailyPosts() != null) user.setMaxDailyPosts(dto.getMaxDailyPosts());        if (dto.getLevel() != null)         user.setLevel(dto.getLevel());        if (dto.getStatus() != null)        user.setStatus(UserStatus.valueOf(dto.getStatus()));        User saved = userRepository.save(user);        return ApiResponse.ok("User updated successfully", toDTO(saved));    }    @Override    @Transactional(readOnly = true)    public ApiResponse getUserById(Long userId) {        User user = userRepository.findById(userId).orElse(null);        if (user == null) {            return ApiResponse.error(4, "User is not found");        }        return ApiResponse.ok("Successful", toDTO(user));    }    @Override    @Transactional    public ApiResponse deactivateUser(Long userId) {        User user = userRepository.findById(userId).orElse(null);        if (user == null) {            return ApiResponse.error(4, "User is not found");        }        user.setStatus(UserStatus.DEACTIVATED);        userRepository.save(user);        return ApiResponse.ok("User is set to DEACTIVATED successfully", null);    }    @Override    @Transactional(readOnly = true)    public ApiResponse getUserList(String role, String status, Pageable pageable) {        Page<User> page = userRepository.search(parseRole(role), parseStatus(status), pageable);        List<UserDTO> content = page.getContent().stream().map(this::toDTO).toList();        PageDTO pageDTO = new PageDTO(                page.getSize(), page.getNumber(), page.getTotalPages(),                page.getTotalElements(), page.isFirst(), page.isLast(), content);        return ApiResponse.ok("Successful", pageDTO);    }    // -- helpers --    private UserDTO toDTO(User u) {        UserDTO d = new UserDTO();        d.setUserId(u.getId());        d.setUsername(u.getUsername());        d.setEmail(u.getEmail());        d.setRole(u.getRole().name());        d.setRatePerPost(u.getRatePerPost());        d.setMaxDailyPosts(u.getMaxDailyPosts());        d.setLevel(u.getLevel());        d.setStatus(u.getStatus().name());        return d;    }    // Parse filter string -> enum; tra null neu rong/khong hop le (bo qua filter)    private Role parseRole(String s) {        if (s == null || s.isBlank()) return null;        try { return Role.valueOf(s); } catch (IllegalArgumentException e) { return null; }    }    private UserStatus parseStatus(String s) {        if (s == null || s.isBlank()) return null;        try { return UserStatus.valueOf(s); } catch (IllegalArgumentException e) { return null; }    }}

Bước 10: Tạo UserController: controller/SE123456UserController.java

Update, getbyid, delete

Get list:

Bước 11: config/CorsConfig.java

Mục đích: cho phép trình duyệt gọi UserService từ một origin khác (cross-origin). Cụ thể ở đây là để trang Swagger UI của MediaGateway (chạy ở :8080) có thể tự fetch trực tiếp http://localhost:8081/v3/api-docs khi gom tài liệu API của cả 3 service vào một trang.

Cách hoạt động: tạo bean CorsFilter (servlet filter, dùng cho ứng dụng Spring MVC) với cấu hình:

addAllowedOriginPattern("*") — chấp nhận mọi origin.

addAllowedMethod("*"), addAllowedHeader("*") — mọi method/header.

setAllowCredentials(false) — bắt buộc phải false khi origin là "*" (spec CORS không cho phép true kết hợp với wildcard origin, nếu để true sẽ ném lỗi lúc runtime).

registerCorsConfiguration("/**", config) — áp dụng cho mọi endpoint, kể cả /v3/api-docs (không chỉ /api/**).

Lưu ý:

Đây là filter cấp servlet nên áp dụng namespace rộng — khác với @CrossOrigin chỉ áp cho 1 controller.

Dùng CorsFilter + UrlBasedCorsConfigurationSource (gói org.springframework.web.filter / org.springframework.web.cors) — không nhầm với bản WebFlux (CorsWebFilter ở gateway, mục 3.4).

Nếu bỏ qua bước này, Swagger UI ở gateway sẽ báo lỗi "Failed to load API definition" do trình duyệt chặn request cross-origin.

Checklist liên quan: mở http://localhost:8080/swagger-ui.html, chọn dropdown user-service phải load được /v3/api-docs của 8081 mà không có lỗi CORS trong console trình duyệt.

package fu.se123456.userservice.config;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import org.springframework.web.cors.CorsConfiguration;import org.springframework.web.cors.UrlBasedCorsConfigurationSource;import org.springframework.web.filter.CorsFilter;/** * CORS cho service MVC (servlet). Cho phép mọi origin. * Cần thiết để Swagger UI của MediaGateway (origin :8080) có thể fetch trực tiếp * /v3/api-docs của service này (cross-origin) khi gom tài liệu. * Dùng CorsFilter (servlet filter) để áp dụng cho MỌI request, kể cả /v3/api-docs. */@Configurationpublic class CorsConfig {    @Bean    public CorsFilter corsFilter() {        CorsConfiguration config = new CorsConfiguration();        config.addAllowedOriginPattern("*");   // mọi origin        config.addAllowedMethod("*");        config.addAllowedHeader("*");        config.setAllowCredentials(false);     // phải false khi origin pattern là "*"        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();        source.registerCorsConfiguration("/**", config);        return new CorsFilter(source);    }}

Bước 12: config/OpenApiConfig.java

Mục đích: cấu hình metadata (tiêu đề, version, mô tả) hiển thị trên trang Swagger UI/OpenAPI của service, đáp ứng yêu cầu bắt buộc "bật Swagger và SpringDoc API" trong đề (mục 2.5.7).

Bean OpenAPI với Info gồm title, version, description — chỉ mang tính hiển thị, không ảnh hưởng logic nghiệp vụ. springdoc-openapi tự động quét controller và sinh /v3/api-docs + /swagger-ui.html dựa trên annotation có sẵn (@RestController, @RequestMapping...), class này chỉ bổ sung phần thông tin chung.

Lưu ý: không bắt buộc phải có class này để Swagger chạy (SpringDoc tự sinh info mặc định), nhưng nên có vì đề yêu cầu Swagger "bật" rõ ràng, và có info riêng giúp dễ phân biệt 3 service khi mở Swagger UI.

Checklist liên quan: vào /swagger-ui.html thấy đúng tiêu đề "User Service API", liệt kê đủ 5 endpoint (POST/PUT/GET/DELETE /api/users, GET /api/users).

package fu.se123456.userservice.config;import io.swagger.v3.oas.models.OpenAPI;import io.swagger.v3.oas.models.info.Info;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;@Configurationpublic class OpenApiConfig {    @Bean    public OpenAPI userServiceOpenApi() {        return new OpenAPI().info(new Info()                .title("User Service API")                .version("1.0.0")                .description("MSS301 2026 Spring PE - User Service (Spring Boot 4.1.0)"));    }}

Checklist kiểm tra UserService

Vào Terminal gõ: mvn clean compile: BUILD SUCCESS, không lỗi Lombok

App start ở port 8081, /swagger-ui.html mở được.

Mở trình duyệt: http://localhost:8081/swagger-ui.html.

Kỳ vọng thấy nhóm SE123456UserController với đúng 5 endpoint: POST /api/users, PUT /api/users/{userId}, GET /api/users/{userId}, DELETE /api/users/{userId}, GET /api/users.

Lưu ý về kiểm tra đường dẫn chạy ứng dụng: Menu Run → Edit Configurations…

Chọn config Se123456UserServiceApplication ở cột trái.

3. POST /api/users hợp lệ → 201, status=1, ACTIVE mặc định

Trên Swagger UI, mở POST /api/users → Try it out → sửa body mẫu thành:

{

"username": "creator_test1",

"email": "test1@example.com",

"role": "CREATOR",

"ratePerPost": 500000,

"maxDailyPosts": 3,

"level": 1

}

(không cần điền status — server tự set ACTIVE). Nhấn Execute.

Kỳ vọng: Response code = 201, body "status": 1, "data.status": "ACTIVE", có userId được sinh ra. Ghi lại userId này để dùng cho các test sau (DELETE, GET).

4. POST trùng username → 226, status=3

Vẫn ở POST /api/users, Try it out lần nữa, dùng lại cùng username creator_test1 (đổi email cho khác nếu muốn), Execute.

Kỳ vọng: Response code = 226, body "status": 3. Lưu ý Swagger UI đôi khi hiển thị mã 226 hơi khác biệt trực quan so với 2xx thường thấy — cứ nhìn vào ô "Server response → Code" là chính xác nhất.

5. POST sai validate (maxDailyPosts=20) → 406, status=2

Try it out với username mới (chưa dùng) và maxDailyPosts: 20 (vượt giới hạn 1–10):

{

"username": "creator_test2",

"email": "test2@example.com",

"role": "CREATOR",

"ratePerPost": 500000,

"maxDailyPosts": 20,

"level": 1

}

Kỳ vọng: Response code = 406, body "status": 2, message nhắc tới maxDailyPosts must be <= 10.

Có thể thử thêm các case sai khác để chắc chắn validate đủ: ratePerPost: 0 (phải >0), email: "abc" (sai định dạng), role: "XXX" (ngoài enum) — tất cả đều phải trả 406/2.

6. DELETE /api/users/{id} → DEACTIVATED, data=null

Mở DELETE /api/users/{userId} → Try it out → điền userId lấy từ bước 3 → Execute.

Kỳ vọng: Response code = 200, body "status": 1, "data": null.

Xác nhận thật sự deactivate: mở tiếp GET /api/users/{userId} với cùng id, Execute → kỳ vọng "data.status": "DEACTIVATED".

7. GET /api/users?role=CREATOR&status=ACTIVE → đúng PageDTO

Trước tiên tạo thêm 1–2 user CREATOR/ACTIVE khác (lặp lại bước 3 với username mới) để có dữ liệu lọc ra kết quả.

Mở GET /api/users → Try it out → điền role = CREATOR, status = ACTIVE (bỏ trống page/size để dùng default) → Execute.

Kỳ vọng: Response code = 200, data chứa đủ 7 field của PageDTO: size, page, totalPages, totalElements, first, last, content (mảng UserDTO) — và mọi phần tử trong content phải có role: "CREATOR", status: "ACTIVE" (user vừa DEACTIVATED ở bước 6 không được xuất hiện).

Thử thêm case lỗi: size=200 → kỳ vọng 406, status=2 (vượt max 100).

Phần 2: Tạo PostService (port 8082) — dùng OpenFeign gọi UserService

Bước 1: Tạo project PostService

Thêm mới các dependencies:

| Dependency trong pom.xml | Từ khóa tìm trên Initializr | Tên hiển thị |
| --- | --- | --- |
| spring-boot-starter-web | Web | Spring Web |
| spring-boot-starter-data-jpa | JPA | Spring Data JPA |
| spring-boot-starter-validation | Validation | Validation |
| mssql-jdbc | SQL Server | MS SQL Server Driver |
| spring-cloud-starter-openfeign | Feign | OpenFeign (nhóm Spring Cloud Routing) |
| spring-boot-starter-test | — | tự động có sẵn, không cần tìm |


Lưu ý quan trọng — 2 phần Initializr KHÔNG có sẵn, phải tự thêm tay vào pom.xml sau khi generate project:

springdoc-openapi-starter-webmvc-ui — Spring Initializr không liệt kê SpringDoc trong danh sách dependency chuẩn. Tạo project xong, mở pom.xml, tự thêm block:

<dependency>

<groupId>org.springdoc</groupId>

<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>

<version>3.0.3</version>

</dependency>

(và thêm <springdoc.version>3.0.3</springdoc.version> vào <properties> nếu muốn quản lý version tập trung).

<dependencyManagement> chứa BOM spring-cloud-dependencies:2025.1.2 — khi bạn chọn "OpenFeign", Initializr thường tự thêm sẵn block dependencyManagement này (vì OpenFeign phụ thuộc BOM Spring Cloud). Sau khi generate, kiểm tra lại đúng version 2025.1.2 (release train Oakwood, tương thích Boot 4.1.0) — nếu Initializr chọn version Spring Cloud khác/không tương thích, sửa tay lại <spring-cloud.version>2025.1.2</spring-cloud.version>.

Một số dependencies cần thiết:

<properties>        <java.version>21</java.version>        <!-- Spring Cloud release train tuong thich Spring Boot 4.1.0 -->        <spring-cloud.version>2025.1.2</spring-cloud.version>    </properties>    <dependencies>        <dependency>            <groupId>org.springframework.boot</groupId>            <artifactId>spring-boot-starter-web</artifactId>        </dependency>        <dependency>            <groupId>org.springframework.boot</groupId>            <artifactId>spring-boot-starter-data-jpa</artifactId>        </dependency>        <dependency>            <groupId>org.springframework.boot</groupId>            <artifactId>spring-boot-starter-validation</artifactId>        </dependency>        <!-- OpenFeign: goi UserService bang @FeignClient.             Version 5.x do BOM spring-cloud-dependencies:2025.1.2 quan ly. -->        <dependency>            <groupId>org.springframework.cloud</groupId>            <artifactId>spring-cloud-starter-openfeign</artifactId>        </dependency>        <dependency>            <groupId>com.microsoft.sqlserver</groupId>            <artifactId>mssql-jdbc</artifactId>            <scope>runtime</scope>        </dependency>        <dependency>            <groupId>org.springframework.boot</groupId>            <artifactId>spring-boot-starter-data-jpa-test</artifactId>            <scope>test</scope>        </dependency>        <dependency>            <groupId>org.springframework.boot</groupId>            <artifactId>spring-boot-starter-validation-test</artifactId>            <scope>test</scope>        </dependency>        <dependency>            <groupId>org.springdoc</groupId>            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>            <version>3.0.3</version>        </dependency>    </dependencies>    <dependencyManagement>        <dependencies>            <dependency>                <groupId>org.springframework.cloud</groupId>                <artifactId>spring-cloud-dependencies</artifactId>                <version>${spring-cloud.version}</version>                <type>pom</type>                <scope>import</scope>            </dependency>        </dependencies>    </dependencyManagement>    <build>        <plugins>            <plugin>                <groupId>org.springframework.boot</groupId>                <artifactId>spring-boot-maven-plugin</artifactId>                <version>4.1.0</version>            </plugin>        </plugins>    </build></project>

Lưu ý:  KHÔNG ghi <version> cho spring-cloud-starter-openfeign — để BOM quản (ra bản 5.0.x).

Bước 2: Cấu hình properties

Bước 3: Cấu hình project

Bước 4: Main class – bật @EnableFeignClients

Bước 5: Tạo Entity

Tạo PostStatus enum

Tạo Entity Post: entity/Post.java

Map với bảng posts trong database

Tạo các lớp common

PostConstants.java: common/PostConstants.java

package fu.se123456.postservice.common;

public final class PostConstants {

private PostConstants() {}

public static final String DRAFT = "DRAFT";

public static final String SCHEDULED = "SCHEDULED";

public static final String PUBLISHED = "PUBLISHED";

public static final String CANCELLED = "CANCELLED";

public static final String STATUS_REGEX = "DRAFT|SCHEDULED|PUBLISHED|CANCELLED";

// User status coming from UserService

public static final String USER_ACTIVE = "ACTIVE";

}

Bước 6: Lớp DTO

Tạo lớp ApiResponse

package fu.se123456.postservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

import java.time.ZoneOffset;

import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.ALWAYS)

public class ApiResponse {

private static final DateTimeFormatter ISO =

DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC);

private int status;

private String message;

private Object data;

private String timestamp;

public ApiResponse() {

}

public ApiResponse(int status, String message, Object data, String timestamp) {

this.status = status;

this.message = message;

this.data = data;

this.timestamp = timestamp;

}

public int getStatus() { return status; }

public void setStatus(int status) { this.status = status; }

public String getMessage() { return message; }

public void setMessage(String message) { this.message = message; }

public Object getData() { return data; }

public void setData(Object data) { this.data = data; }

public String getTimestamp() { return timestamp; }

public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

public static ApiResponse of(int status, String message, Object data) {

return new ApiResponse(status, message, data, ISO.format(Instant.now()));

}

public static ApiResponse ok(String message, Object data) {

return of(1, message, data);

}

public static ApiResponse error(int status, String message) {

return of(status, message, null);

}

}

Tạo PageDTO:

PageDTO (Post) — same structure as PageDTO in User Service, content = List<PostDetailDTO>.

package fu.se123456.postservice.dto;

public class PageDTO {

private int size;

private int page;

private int totalPages;

private long totalElements;

private boolean first;

private boolean last;

private Object content;   // List<PostDetailDTO>

public PageDTO() {

}

public PageDTO(int size, int page, int totalPages, long totalElements, boolean first, boolean last, Object content) {

this.size = size;

this.page = page;

this.totalPages = totalPages;

this.totalElements = totalElements;

this.first = first;

this.last = last;

this.content = content;

}

public int getSize() { return size; }

public void setSize(int size) { this.size = size; }

public int getPage() { return page; }

public void setPage(int page) { this.page = page; }

public int getTotalPages() { return totalPages; }

public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

public long getTotalElements() { return totalElements; }

public void setTotalElements(long totalElements) { this.totalElements = totalElements; }

public boolean isFirst() { return first; }

public void setFirst(boolean first) { this.first = first; }

public boolean isLast() { return last; }

public void setLast(boolean last) { this.last = last; }

public Object getContent() { return content; }

public void setContent(Object content) { this.content = content; }

}

Tạo lớp PostDTO

package fu.se123456.postservice.dto;import com.fasterxml.jackson.annotation.JsonFormat;import fu.se123456.postservice.common.PostConstants;import jakarta.validation.constraints.*;import java.math.BigDecimal;import java.util.Date;public class PostDTO {    private Long postId;    @NotBlank(message = "advertiserName is required")    @Size(max = 100)    private String advertiserName;    @NotBlank(message = "advertiserEmail is required")    @Email(message = "advertiserEmail is invalid")    @Size(max = 100)    private String advertiserEmail;    @NotBlank(message = "advertiserPhone is required")    @Size(max = 20)    private String advertiserPhone;    @NotNull(message = "startDate is required")    @JsonFormat(pattern = "dd/MM/yyyy")    private Date startDate;    @NotNull(message = "endDate is required")    @JsonFormat(pattern = "dd/MM/yyyy")    private Date endDate;    @NotNull(message = "numberOfPosts is required")    @Min(value = 1, message = "numberOfPosts must be >= 1")    private Integer numberOfPosts;    // Computed by the server = ratePerPost x number of days    private BigDecimal totalCost;    // Optional; defaults to SCHEDULED on create    @Pattern(regexp = PostConstants.STATUS_REGEX,            message = "status must be DRAFT, SCHEDULED, PUBLISHED or CANCELLED")    private String status;    @NotNull(message = "userId is required")    private Long userId;    public Long getPostId() { return postId; }    public void setPostId(Long postId) { this.postId = postId; }    public String getAdvertiserName() { return advertiserName; }    public void setAdvertiserName(String advertiserName) { this.advertiserName = advertiserName; }    public String getAdvertiserEmail() { return advertiserEmail; }    public void setAdvertiserEmail(String advertiserEmail) { this.advertiserEmail = advertiserEmail; }    public String getAdvertiserPhone() { return advertiserPhone; }    public void setAdvertiserPhone(String advertiserPhone) { this.advertiserPhone = advertiserPhone; }    public Date getStartDate() { return startDate; }    public void setStartDate(Date startDate) { this.startDate = startDate; }    public Date getEndDate() { return endDate; }    public void setEndDate(Date endDate) { this.endDate = endDate; }    public Integer getNumberOfPosts() { return numberOfPosts; }    public void setNumberOfPosts(Integer numberOfPosts) { this.numberOfPosts = numberOfPosts; }    public BigDecimal getTotalCost() { return totalCost; }    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }    public String getStatus() { return status; }    public void setStatus(String status) { this.status = status; }    public Long getUserId() { return userId; }    public void setUserId(Long userId) { this.userId = userId; }}

Tạo UserDTO: dto/UserDTO.java (bản sao cục bộ, nhận dữ liệu qua Feign)

Có cấu trúc giống UserDTO ở user service nhưng UserDTO ở PostService không có annotation validate (@NotBlank, @Pattern...) như bản gốc bên UserService — vì PostService không dùng nó để nhận request tạo user (không cần validate), mà chỉ dùng để đọc dữ liệu user đã tồn tại (chỉ cần field đúng tên để Jackson map JSON → object).

Nếu sau này UserDTO bên UserService đổi cấu trúc (đổi tên field, thêm/bớt field) thì phải tự tay cập nhật lại bản sao bên PostService cho khớp — đây là cái giá phải trả cho sự tách biệt/độc lập giữa các service (loose coupling), đổi lại là mỗi service có thể phát triển, build, deploy độc lập mà không phụ thuộc lẫn nhau về code.

package fu.se123456.postservice.dto;

import java.math.BigDecimal;

/** Local copy of the UserService UserDTO (data fetched via OpenFeign). */

public class UserDTO {

private Long userId;

private String username;

private String email;

private String role;

private BigDecimal ratePerPost;

private Integer maxDailyPosts;

private Integer level;

private String status;

public Long getUserId() { return userId; }

public void setUserId(Long userId) { this.userId = userId; }

public String getUsername() { return username; }

public void setUsername(String username) { this.username = username; }

public String getEmail() { return email; }

public void setEmail(String email) { this.email = email; }

public String getRole() { return role; }

public void setRole(String role) { this.role = role; }

public BigDecimal getRatePerPost() { return ratePerPost; }

public void setRatePerPost(BigDecimal ratePerPost) { this.ratePerPost = ratePerPost; }

public Integer getMaxDailyPosts() { return maxDailyPosts; }

public void setMaxDailyPosts(Integer maxDailyPosts) { this.maxDailyPosts = maxDailyPosts; }

public Integer getLevel() { return level; }

public void setLevel(Integer level) { this.level = level; }

public String getStatus() { return status; }

public void setStatus(String status) { this.status = status; }

}

Tạo lớp PostDetailDTO

DTO — dto/PostDetailDTO.java (thay userId bằng object user)

package fu.se123456.postservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

import java.util.Date;

public class PostDetailDTO {

private Long postId;

private String advertiserName;

private String advertiserEmail;

private String advertiserPhone;

@JsonFormat(pattern = "dd/MM/yyyy")

private Date startDate;

@JsonFormat(pattern = "dd/MM/yyyy")

private Date endDate;

private Integer numberOfPosts;

private BigDecimal totalCost;

private String status;

// Full user detail fetched from UserService

private UserDTO user;

public Long getPostId() { return postId; }

public void setPostId(Long postId) { this.postId = postId; }

public String getAdvertiserName() { return advertiserName; }

public void setAdvertiserName(String advertiserName) { this.advertiserName = advertiserName; }

public String getAdvertiserEmail() { return advertiserEmail; }

public void setAdvertiserEmail(String advertiserEmail) { this.advertiserEmail = advertiserEmail; }

public String getAdvertiserPhone() { return advertiserPhone; }

public void setAdvertiserPhone(String advertiserPhone) { this.advertiserPhone = advertiserPhone; }

public Date getStartDate() { return startDate; }

public void setStartDate(Date startDate) { this.startDate = startDate; }

public Date getEndDate() { return endDate; }

public void setEndDate(Date endDate) { this.endDate = endDate; }

public Integer getNumberOfPosts() { return numberOfPosts; }

public void setNumberOfPosts(Integer numberOfPosts) { this.numberOfPosts = numberOfPosts; }

public BigDecimal getTotalCost() { return totalCost; }

public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }

public String getStatus() { return status; }

public void setStatus(String status) { this.status = status; }

public UserDTO getUser() { return user; }

public void setUser(UserDTO user) { this.user = user; }

}

Tạo lớp:  common/GlobalExceptionHandler.java

package fu.se123456.postservice.common;

import fu.se123456.postservice.dto.ApiResponse;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class GlobalExceptionHandler {

@ExceptionHandler(Exception.class)

public ResponseEntity<ApiResponse> handleAll(Exception ex) {

return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

.body(ApiResponse.error(0, "Internal server error: " + ex.getMessage()));

}

}

Bước 7: Tạo Repository

package fu.se123456.postservice.repository;

import fu.se123456.postservice.entity.Post;

import fu.se123456.postservice.entity.PostStatus;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

@Query("""

SELECT p FROM Post p

WHERE (:advertiserName IS NULL OR LOWER(p.advertiserName) LIKE LOWER(CONCAT('%', :advertiserName, '%')))

AND (:status IS NULL OR p.status = :status)

""")

Page<Post> search(@Param("advertiserName") String advertiserName,

@Param("status") PostStatus status,

Pageable pageable);

}

Bước 8: Cấu hình OpenFeign: client/UserClient.java — @FeignClient gọi UserService

Đây là bước tạo ra "cầu nối" để PostService gọi được sang UserService qua HTTP, phục vụ đúng các yêu cầu nghiệp vụ trong đề mà PostService tự nó không có đủ dữ liệu để tự làm:

Kiểm tra creator tồn tại & đang ACTIVE: PostService chỉ có bảng posts, không có bảng users — muốn biết userId gửi lên có tồn tại không, trạng thái có phải ACTIVE không, bắt buộc phải hỏi UserService. UserClient.getUserById(userId) chính là lời gọi thực hiện việc "hỏi" đó.

Lấy ratePerPost để tính totalCost: công thức totalCost = ratePerPost × số ngày cần ratePerPost — dữ liệu này chỉ nằm bên UserService, PostService phải fetch về qua UserClient mới tính được (dùng trong cả createPost và updatePost khi tính lại giá).

Lấy maxDailyPosts để validate numberOfPosts: rule "numberOfPosts không vượt maxDailyPosts của user" cũng cần dữ liệu từ UserService.

Trả PostDetailDTO có object user đầy đủ ở endpoint GET /api/posts/{id} — DTO này yêu cầu nhúng nguyên object UserDTO (username, role, ratePerPost...), lấy được cũng nhờ gọi qua UserClient.

Nói ngắn gọn: không có UserClient thì PostServiceImpl không có cách nào lấy dữ liệu user để thực hiện 4 việc trên — đây là bước kỹ thuật bắt buộc để hiện thực hoá yêu cầu "giao tiếp giữa PostService và UserService bằng OpenFeign" mà đề bài (mục 2.2, ghi chú UserService không dùng Feign còn PostService phải dùng) đặt ra.

@FeignClient(name = "user-service", url = "${user.service.url}")

public interface UserClient {

@GetMapping("/api/users/{userId}")

ApiResponse getUserById(@PathVariable("userId") Long userId);

}

Mục đích

Đây là declarative HTTP client — thay vì tự viết code gọi RestTemplate/RestClient (build URL, set header, gửi request, parse JSON tay), bạn chỉ cần khai báo một interface mô tả "endpoint này trông như thế nào", OpenFeign sẽ tự sinh implementation ở runtime để thực hiện lời gọi HTTP thật.

Giải thích từng phần

public interface UserClient — chỉ là interface, không có method body. Bạn không tự viết code gọi HTTP — Spring Cloud OpenFeign sẽ tạo ra một dynamic proxy implement interface này lúc ứng dụng khởi động (nhờ @EnableFeignClients đặt trên main class quét thấy interface có @FeignClient), rồi đăng ký proxy đó thành Spring Bean. Vì vậy trong PostServiceImpl bạn chỉ cần @Autowired/constructor-inject UserClient và gọi userClient.getUserById(id) như một method Java bình thường — mọi việc dựng HTTP request, gửi đi, nhận response, parse JSON đều được proxy lo phía sau.

@FeignClient(name = "user-service", url = "${user.service.url}")

name = "user-service" — tên định danh logic cho client này. Tên này khớp với các property cấu hình riêng cho client trong application.properties:

spring.cloud.openfeign.client.config.user-service.connect-timeout=3000

spring.cloud.openfeign.client.config.user-service.read-timeout=5000

spring.cloud.openfeign.client.config.user-service.logger-level=basic

Feign dùng đúng chuỗi user-service để tra ra các config timeout/logging này áp dụng cho client nào.

url = "${user.service.url}" — chỉ định thẳng địa chỉ UserService (http://localhost:8081, lấy từ property user.service.url). Trong microservices "chuẩn" có service discovery (Eureka/Consul), bạn thường chỉ cần name và Feign tự tra host qua registry + load balancer. Ở bài thi này không có service registry, nên phải chỉ định url cố định để Feign biết gọi thẳng vào đâu.

@GetMapping("/api/users/{userId}") + @PathVariable("userId") Long userId — Feign tái sử dụng annotation Spring MVC (SpringMvcContract) để mô tả request: method GET, path /api/users/{userId}, biến {userId} trong URL được thay bằng giá trị tham số truyền vào khi gọi method. Kết quả: gọi userClient.getUserById(5L) → Feign build và gửi GET http://localhost:8081/api/users/5.

Kiểu trả về ApiResponse — Feign tự động deserialize JSON body của response thành object ApiResponse (dùng cùng cơ chế Jackson converter mà Spring Boot đã cấu hình sẵn) — bạn nhận lại object Java, không phải chuỗi JSON thô.

Lưu ý

Interface này không cần implement gì, không cần @Component/@Service — chỉ cần đứng đúng vị trí để @EnableFeignClients quét thấy (thường đặt trong package con client, nằm dưới package gốc của @SpringBootApplication).

Nếu UserService trả về 404 (user không tồn tại), Feign mặc định sẽ ném FeignException.NotFound ngay tại lời gọi userClient.getUserById(...) — đây chính là lý do bên PostServiceImpl.fetchUser() phải bọc try/catch (FeignException.NotFound ex) để chuyển thành status=4 thay vì để rơi ra lỗi 500 (đã giải thích ở các bước trước).

URL trong @FeignClient dùng ${...} nên bắt buộc application.properties của PostService phải có user.service.url=http://localhost:8081, thiếu property này ứng dụng sẽ lỗi khi khởi động (không resolve được placeholder).

Checklist liên quan

Interface nằm trong package client, có @FeignClient(name="user-service", url="${user.service.url}").

Main class PostService có @EnableFeignClients.

application.properties có đủ user.service.url + 3 dòng cấu hình spring.cloud.openfeign.client.config.user-service.*.

PostServiceImpl bắt đúng FeignException.NotFound khi gọi userClient.getUserById(...).

Bước 9: config/HttpClientConfig.java (bean ObjectMapper để convert Object data)

Cung cấp bean ObjectMapper để PostServiceImpl dùng convertValue() chuyển dữ liệu thô (Object/LinkedHashMap) trong ApiResponse.data nhận qua Feign thành đúng object UserDTO.

package fu.se123456.postservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

/**

* OpenFeign variant: UserClient duoc OpenFeign tu tao (xem @EnableFeignClients),

* nen KHONG tao proxy RestClient o day nua.

* Chi giu lai ObjectMapper de service dung convertValue(data -> UserDTO).

*/

@Configuration

public class HttpClientConfig {

@Bean

public ObjectMapper objectMapper() {

return new ObjectMapper();

}

}

Vấn đề cần giải quyết

Nhìn lại ApiResponse:

private Object data;   // có thể là null / 1 object / list object

Field data được khai kiểu Object (generic, vì ApiResponse dùng chung cho mọi loại response — lúc là UserDTO, lúc là PageDTO, lúc là null).

Khi UserClient.getUserById(userId) (Feign) nhận response JSON từ UserService và deserialize thành ApiResponse, Jackson không biết field data thực chất nên là kiểu gì (vì khai Object) → Jackson mặc định deserialize nó thành LinkedHashMap<String, Object> (một map key-value thô), không phải instance của UserDTO. Nếu bạn cố ép kiểu trực tiếp (UserDTO) res.getData() sẽ bị ClassCastException.

Bean ObjectMapper giải quyết vấn đề này như thế nào

HttpClientConfig khai một bean ObjectMapper để inject vào PostServiceImpl (qua constructor), dùng ở fetchUser():

return objectMapper.convertValue(res.getData(), UserDTO.class);

convertValue(...) lấy cái LinkedHashMap thô đó, chuyển đổi lại (re-serialize → deserialize) thành đúng object UserDTO — từ đó mới gọi được user.getRatePerPost(), user.getMaxDailyPosts(), user.getStatus()... như một object Java bình thường.

Vì sao comment "KHÔNG tạo proxy RestClient"

Nếu bài làm theo cách không dùng OpenFeign (dùng RestClient/HttpServiceProxyFactory để tự gọi HTTP thủ công), class config này thường sẽ có thêm 1 bean tạo proxy gọi UserService. Nhưng vì bài dùng OpenFeign, UserClient đã tự động được @EnableFeignClients sinh ra rồi (không cần khai proxy tay) → HttpClientConfig ở đây chỉ còn nhiệm vụ duy nhất: cung cấp ObjectMapper.

Bước 10: config/CorsConfig.java

Mục đích

Cho phép trình duyệt gọi PostService (port 8082) từ origin khác — cụ thể để trang Swagger UI của MediaGateway (:8080) tự fetch trực tiếp http://localhost:8082/v3/api-docs khi gom tài liệu API của cả 3 service vào một trang (giống hệt lý do đã giải thích ở CorsConfig bên UserService, chỉ khác service/port).

package fu.se123456.postservice.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.web.filter.CorsFilter;

/**

* CORS cho service MVC (servlet). Cho phép mọi origin.

* Cần thiết để Swagger UI của MediaGateway (origin :8080) có thể fetch trực tiếp

* /v3/api-docs của service này (cross-origin) khi gom tài liệu.

* Dùng CorsFilter (servlet filter) để áp dụng cho MỌI request, kể cả /v3/api-docs.

*/

@Configuration

public class CorsConfig {

@Bean

public CorsFilter corsFilter() {

CorsConfiguration config = new CorsConfiguration();

config.addAllowedOriginPattern("*");   // mọi origin

config.addAllowedMethod("*");

config.addAllowedHeader("*");

config.setAllowCredentials(false);     // phải false khi origin pattern là "*"

UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

source.registerCorsConfiguration("/**", config);

return new CorsFilter(source);

}

}

Giải thích từng dòng

CorsConfiguration config = new CorsConfiguration(); — tạo object cấu hình CORS rỗng.

addAllowedOriginPattern("*") — chấp nhận request từ mọi origin (mọi domain/port gọi tới).

addAllowedMethod("*") — cho phép mọi HTTP method (GET, POST, PUT, DELETE...).

addAllowedHeader("*") — cho phép mọi header trong request.

setAllowCredentials(false) — bắt buộc để false khi origin là "*" (spec CORS không cho phép kết hợp wildcard origin với credentials true, để true sẽ lỗi runtime).

UrlBasedCorsConfigurationSource source + registerCorsConfiguration("/**", config) — áp dụng cấu hình CORS trên cho mọi endpoint (/**), bao gồm cả /api/posts/** lẫn /v3/api-docs.

return new CorsFilter(source); — trả về bean CorsFilter, một servlet filter chạy trước mọi request, tự thêm header CORS (Access-Control-Allow-Origin...) vào response.

Lưu ý

Dùng CorsFilter (gói org.springframework.web.filter) — filter cấp servlet, áp dụng namespace rộng hơn @CrossOrigin (vốn chỉ áp cho 1 controller cụ thể).

Đây là bản dùng cho Spring MVC (servlet) — khác với CorsWebFilter dùng ở MediaGateway (WebFlux, mục 3.4). Hai loại không hoán đổi cho nhau được.

Nếu thiếu class này, Swagger UI ở gateway sẽ báo lỗi "Failed to load API definition" khi chọn post-service trong dropdown.

Checklist kiểm tra

Bean CorsFilter tồn tại trong package config của PostService.

Mở http://localhost:8080/swagger-ui.html, chọn dropdown post-service → load được /v3/api-docs của :8082 mà không có lỗi CORS trong console trình duyệt.

Gọi trực tiếp http://localhost:8082/api/posts từ một trang HTML/JS khác origin (nếu cần test riêng) → không bị trình duyệt chặn.

Bước 11: config/OpenApiConfig.java

Cấu hình metadata (tiêu đề, version, mô tả) hiển thị trên trang Swagger UI/OpenAPI của PostService, đáp ứng yêu cầu bắt buộc "bật Swagger và SpringDoc API" (mục 2.5.7 đề bài). Bean OpenAPI chỉ mang tính hiển thị/thông tin, không ảnh hưởng logic nghiệp vụ hay danh sách endpoint — SpringDoc tự động quét @RestController (SE123456PostController) để sinh phần liệt kê endpoint, class này chỉ bổ sung phần "Info" chung ở đầu trang.

package fu.se123456.postservice.config;

import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

@Configuration

public class OpenApiConfig {

@Bean

public OpenAPI postServiceOpenApi() {

return new OpenAPI().info(new Info()

.title("Post Service API")

.version("1.0.0")

.description("MSS301 2026 Spring PE - Post Service (Spring Boot 4.1.0)"));

}

}

Chạy ứng dụng PostService

Chạy swagger để kiểm tra:

http://localhost:8082/swagger-ui/index.html

Bước 12: Tạo service: service/PostService.java (interface)

package fu.se123456.postservice.service;

import fu.se123456.postservice.dto.ApiResponse;

import fu.se123456.postservice.dto.PostDTO;

import org.springframework.data.domain.Pageable;

public interface PostService {

ApiResponse createPost(PostDTO dto);

ApiResponse updatePost(Long postId, PostDTO dto);

ApiResponse getPostById(Long postId);

ApiResponse cancelPost(Long postId);

ApiResponse getPostList(String advertiserName, String status, Pageable pageable);

}

Bước 13: Tạo PostServiceImpl: service/impl/PostServiceImpl.java — logic + xử lý 404 của Feign

Thứ tự validate trong createPost (quan trọng để ra đúng mã lỗi):

endDate phải sau startDate → sai: 406/2.

Gọi Feign lấy user; user không tồn tại (Feign 404) → 404/4.

user.status != ACTIVE → 400/5.

numberOfPosts > user.maxDailyPosts → 406/2.

Qua hết → lưu với status = SCHEDULED, totalCost tự tính.

Nếu không bắt FeignException.NotFound, exception sẽ rơi vào GlobalExceptionHandler → HTTP 500/status=0 — sai (đề cần 404/4). Cách thay thế: bật spring.cloud.openfeign.client.config.user-service.dismiss404=true để Feign trả body rỗng thay vì ném exception; khi đó nhánh res.getData()==null tự xử lý.

package fu.se123456.postservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;

import fu.se123456.postservice.client.UserClient;

import fu.se123456.postservice.common.PostConstants;

import fu.se123456.postservice.dto.*;

import fu.se123456.postservice.entity.Post;

import fu.se123456.postservice.entity.PostStatus;

import fu.se123456.postservice.repository.PostRepository;

import fu.se123456.postservice.service.PostService;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.time.ZoneId;

import java.time.temporal.ChronoUnit;

import java.util.Date;

import java.util.List;

@Service

public class PostServiceImpl implements PostService {

private final PostRepository postRepository;

private final UserClient userClient;

private final ObjectMapper objectMapper;

public PostServiceImpl(PostRepository postRepository, UserClient userClient, ObjectMapper objectMapper) {

this.postRepository = postRepository;

this.userClient = userClient;

this.objectMapper = objectMapper;

}

@Override

@Transactional

public ApiResponse createPost(PostDTO dto) {

// Date validation (status 2 -> 406)

if (!dto.getEndDate().after(dto.getStartDate())) {

return ApiResponse.error(2, "endDate must be after startDate");

}

// Fetch user from UserService via OpenFeign

UserDTO user = fetchUser(dto.getUserId());

if (user == null) {

return ApiResponse.error(4, "User ID is not found");   // 404

}

if (!PostConstants.USER_ACTIVE.equals(user.getStatus())) {

return ApiResponse.error(5, "User is not ACTIVE for posting"); // 400

}

if (dto.getNumberOfPosts() > user.getMaxDailyPosts()) {

return ApiResponse.error(2, "numberOfPosts must not exceed user's maxDailyPosts"); // 406

}

Post p = new Post();

p.setAdvertiserName(dto.getAdvertiserName());

p.setAdvertiserEmail(dto.getAdvertiserEmail());

p.setAdvertiserPhone(dto.getAdvertiserPhone());

p.setStartDate(dto.getStartDate());

p.setEndDate(dto.getEndDate());

p.setNumberOfPosts(dto.getNumberOfPosts());

p.setUserId(dto.getUserId());

p.setTotalCost(computeTotal(user.getRatePerPost(), dto.getStartDate(), dto.getEndDate()));

p.setStatus(PostStatus.SCHEDULED); // default SCHEDULED

Post saved = postRepository.save(p);

return ApiResponse.of(1, "Post campaign created successfully", toDTO(saved));

}

@Override

@Transactional

public ApiResponse updatePost(Long postId, PostDTO dto) {

Post p = postRepository.findById(postId).orElse(null);

if (p == null) {

return ApiResponse.error(4, "Post Id is not found");

}

// Validate the EFFECTIVE dates BEFORE touching the managed entity.

// (p is already attached/managed here; mutating it and returning early

// would still let Hibernate's dirty-checking flush the bad values on

// commit, tripping the DB CHECK constraint and surfacing as a 500.)

Date effectiveStart = dto.getStartDate() != null ? dto.getStartDate() : p.getStartDate();

Date effectiveEnd   = dto.getEndDate()   != null ? dto.getEndDate()   : p.getEndDate();

if (!effectiveEnd.after(effectiveStart)) {

return ApiResponse.error(2, "endDate must be after startDate");

}

// Partial update (safe now that dates are validated)

if (dto.getAdvertiserName() != null)  p.setAdvertiserName(dto.getAdvertiserName());

if (dto.getAdvertiserEmail() != null) p.setAdvertiserEmail(dto.getAdvertiserEmail());

if (dto.getAdvertiserPhone() != null) p.setAdvertiserPhone(dto.getAdvertiserPhone());

p.setStartDate(effectiveStart);

p.setEndDate(effectiveEnd);

if (dto.getNumberOfPosts() != null) p.setNumberOfPosts(dto.getNumberOfPosts());

if (dto.getStatus() != null) p.setStatus(PostStatus.valueOf(dto.getStatus()));

// Recompute total cost based on the current user rate

UserDTO user = fetchUser(p.getUserId());

if (user != null) {

p.setTotalCost(computeTotal(user.getRatePerPost(), p.getStartDate(), p.getEndDate()));

}

Post saved = postRepository.save(p);

return ApiResponse.ok("Post campaign updated successfully", toDTO(saved));

}

@Override

@Transactional(readOnly = true)

public ApiResponse getPostById(Long postId) {

Post p = postRepository.findById(postId).orElse(null);

if (p == null) {

return ApiResponse.error(4, "Post is not found");

}

return ApiResponse.ok("Successful", toDetailDTO(p));

}

@Override

@Transactional

public ApiResponse cancelPost(Long postId) {

Post p = postRepository.findById(postId).orElse(null);

if (p == null) {

return ApiResponse.error(4, "Post is not found");

}

p.setStatus(PostStatus.CANCELLED);

postRepository.save(p);

return ApiResponse.ok("Post campaign is cancelled successfully", null);

}

@Override

@Transactional(readOnly = true)

public ApiResponse getPostList(String advertiserName, String status, Pageable pageable) {

Page<Post> page = postRepository.search(emptyToNull(advertiserName), parsePostStatus(status), pageable);

List<PostDetailDTO> content = page.getContent().stream().map(this::toDetailDTO).toList();

PageDTO pageDTO = new PageDTO(

page.getSize(), page.getNumber(), page.getTotalPages(),

page.getTotalElements(), page.isFirst(), page.isLast(), content);

return ApiResponse.ok("Successful", pageDTO);

}

// -- helpers --

/** Goi UserService qua OpenFeign; tra null khi user khong ton tai (HTTP 404). */

private UserDTO fetchUser(Long userId) {

try {

ApiResponse res = userClient.getUserById(userId);

if (res == null || res.getStatus() != 1 || res.getData() == null) {

return null;

}

return objectMapper.convertValue(res.getData(), UserDTO.class);

} catch (FeignException.NotFound ex) {

// 404 tu UserService -> coi nhu khong tim thay user (status 4)

return null;

}

// Cac FeignException khac (503, timeout...) se duoc nem tiep

// va GlobalExceptionHandler tra 500/status=0.

}

private BigDecimal computeTotal(BigDecimal ratePerPost, Date start, Date end) {

long days = ChronoUnit.DAYS.between(toLocalDate(start), toLocalDate(end));

if (days < 1) days = 1;

return ratePerPost.multiply(BigDecimal.valueOf(days));

}

private java.time.LocalDate toLocalDate(Date date) {

return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

}

private PostDTO toDTO(Post p) {

PostDTO d = new PostDTO();

d.setPostId(p.getPostId());

d.setAdvertiserName(p.getAdvertiserName());

d.setAdvertiserEmail(p.getAdvertiserEmail());

d.setAdvertiserPhone(p.getAdvertiserPhone());

d.setStartDate(p.getStartDate());

d.setEndDate(p.getEndDate());

d.setNumberOfPosts(p.getNumberOfPosts());

d.setTotalCost(p.getTotalCost());

d.setStatus(p.getStatus().name());

d.setUserId(p.getUserId());

return d;

}

private PostDetailDTO toDetailDTO(Post p) {

PostDetailDTO d = new PostDetailDTO();

d.setPostId(p.getPostId());

d.setAdvertiserName(p.getAdvertiserName());

d.setAdvertiserEmail(p.getAdvertiserEmail());

d.setAdvertiserPhone(p.getAdvertiserPhone());

d.setStartDate(p.getStartDate());

d.setEndDate(p.getEndDate());

d.setNumberOfPosts(p.getNumberOfPosts());

d.setTotalCost(p.getTotalCost());

d.setStatus(p.getStatus().name());

d.setUser(fetchUser(p.getUserId())); // may be null if user removed

return d;

}

private String emptyToNull(String s) {

return (s == null || s.isBlank()) ? null : s;

}

// Parse filter string -> enum; tra null neu rong/khong hop le (bo qua filter)

private PostStatus parsePostStatus(String s) {

if (s == null || s.isBlank()) return null;

try { return PostStatus.valueOf(s); } catch (IllegalArgumentException e) { return null; }

}

}

Bước 14: Tạo controller: controller/SE123456PostController.java

Lưu ý đặt tên class đúng theo yêu cầu của đề bài

Checklist Bước 2 (PostService)

[ ] pom có spring-cloud-starter-openfeign (không ghi version) + BOM spring-cloud-dependencies:2025.1.2.

[ ] Main class có @EnableFeignClients.

[ ] UserClient gọi đúng ${user.service.url}/api/users/{id}.

[ ] Bắt FeignException.NotFound → trả status 4 (không để rơi 500).

[ ] App chạy port 8082.

[ ] POST /api/posts với user ACTIVE → 201/1, SCHEDULED, totalCost = ratePerPost × số ngày.

{

"advertiserName": "Shop ABC",

"advertiserEmail": "ads@example.com",

"advertiserPhone": "0900000000",

"startDate": "25/07/2026",

"endDate": "28/07/2026",

"numberOfPosts": 2,

"userId": 1

}

Kỳ vọng: HTTP 201, status=1, data.status="SCHEDULED", data.totalCost = ratePerPost × số ngày. Với giá 500000, và 3 ngày (25→28): 1500000

Công thức: days = ngày(endDate) − ngày(startDate), tối thiểu 1.

[ ] POST với userId không tồn tại → 404/4.

{

"advertiserName": "Shop ABC",

"advertiserEmail": "ads@example.com",

"advertiserPhone": "0900000000",

"startDate": "25/07/2026",

"endDate": "28/07/2026",

"numberOfPosts": 2,

"userId": 10

}

[ ] POST với user SUSPENDED → 400/5.

[ ] POST với vi phạm validation → 406 / status 2

Ví dụ:

[ ] GET /api/posts/{id} → PostDetailDTO có object user đầy đủ.

Postid không tồn tại:

[ ] PUT /api/posts/{id}, chỉnh sửa postid =1 → 200 / status 1, tính lại totalCost

{

"advertiserName": "Nguyen Van A",

"advertiserEmail": "a@example.com",

"advertiserPhone": "0901234567",

"startDate": "25/07/2026",

"endDate": "27/07/2026",

"numberOfPosts": 3,

"status": "SCHEDULED",

"userId": 1

}

Tương tự

PUT id không tồn tại → 404/4.

PUT khiến endDate ≤ startDate → 406/2.

[ ] DELETE /api/posts/{id}, DELETE = hủy (CANCELLED) → 200 / status 1

Kỳ vọng DELETE: HTTP 200, status=1, data=null; GET lại thấy status="CANCELLED".

DELETE id không tồn tại → 404/4.

Phần 3: MediaGateway (port 8080)

Bước 1: Tạo mới project

New Project, Artifact mediagateway, Name <StudentID>MediaGateway. Dùng WebFlux — KHÔNG thêm spring-boot-starter-web.

Tìm từ khóa: gateway (id: cloud-gateway) → đây chính là Spring Cloud Gateway, sẽ kéo về spring-cloud-starter-gateway-server-webflux với Spring Boot 4.1/Spring Cloud 2025.1 (comment trong pom cũng ghi rõ tên starter đã đổi).

Còn lại:

springdoc-openapi-starter-webflux-ui không có trong danh sách dependency của Spring Initializr — phải thêm tay vào pom.xml sau khi generate project.

spring-boot-starter-test được Initializr tự thêm mặc định cho mọi project, không cần tìm.

<properties>        <java.version>21</java.version>        <!-- Spring Cloud 2025.1.x (Oakwood) is the release train built on Spring Boot 4 -->        <spring-cloud.version>2025.1.2</spring-cloud.version>        <springdoc.version>3.0.3</springdoc.version>    </properties>    <dependencies>        <!-- Spring Cloud Gateway (WebFlux). In 2025.1 the starter was renamed:             old: spring-cloud-starter-gateway             new: spring-cloud-starter-gateway-server-webflux -->        <dependency>            <groupId>org.springframework.cloud</groupId>            <artifactId>spring-cloud-starter-gateway-server-webflux</artifactId>        </dependency>        <!-- Swagger UI for a WebFlux app -> use the webflux-ui starter -->        <dependency>            <groupId>org.springdoc</groupId>            <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>            <version>${springdoc.version}</version>        </dependency>        <dependency>            <groupId>org.springframework.boot</groupId>            <artifactId>spring-boot-starter-test</artifactId>            <scope>test</scope>        </dependency>    </dependencies>    <dependencyManagement>        <dependencies>            <dependency>                <groupId>org.springframework.cloud</groupId>                <artifactId>spring-cloud-dependencies</artifactId>                <version>${spring-cloud.version}</version>                <type>pom</type>                <scope>import</scope>            </dependency>        </dependencies>    </dependencyManagement>    <build>        <plugins>            <plugin>                <groupId>org.springframework.boot</groupId>                <artifactId>spring-boot-maven-plugin</artifactId>            </plugin>        </plugins>    </build></project>

Bước 2: Cấu hình properties

spring.application.name=SE123456MediaGatewayserver.port=8080# -- Routes --# User Servicespring.cloud.gateway.server.webflux.routes[0].id=user-servicespring.cloud.gateway.server.webflux.routes[0].uri=http://localhost:8081spring.cloud.gateway.server.webflux.routes[0].predicates[0]=Path=/api/users/**# Post Servicespring.cloud.gateway.server.webflux.routes[1].id=post-servicespring.cloud.gateway.server.webflux.routes[1].uri=http://localhost:8082spring.cloud.gateway.server.webflux.routes[1].predicates[0]=Path=/api/posts/**# -- Swagger / SpringDoc --springdoc.swagger-ui.path=/swagger-ui.htmlspringdoc.api-docs.path=/v3/api-docs# Aggregate the downstream service docs in the gateway Swagger UIspringdoc.swagger-ui.urls[0].name=user-servicespringdoc.swagger-ui.urls[0].url=http://localhost:8081/v3/api-docsspringdoc.swagger-ui.urls[1].name=post-servicespringdoc.swagger-ui.urls[1].url=http://localhost:8082/v3/api-docs# CORS is configured in CorsConfig.java via CorsWebFilter

Bước 3: config/CorsConfig.java — dùng CorsWebFilter (WebFlux), không WebMvcConfigurer

package fu.se123456.mediagateway.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import org.springframework.web.cors.reactive.CorsWebFilter;

/**

* CORS for a reactive (WebFlux) gateway. Accepts connections from all hosts.

* Uses CorsWebFilter (WebFlux) - NOT WebMvcConfigurer.

*/

@Configuration

public class CorsConfig {

@Bean

public CorsWebFilter corsWebFilter() {

CorsConfiguration config = new CorsConfiguration();

config.addAllowedOriginPattern("*");   // all hosts

config.addAllowedMethod("*");

config.addAllowedHeader("*");

config.setAllowCredentials(false);     // must be false when origin pattern is "*"

UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

source.registerCorsConfiguration("/**", config);

return new CorsWebFilter(source);

}

}

Checklist Bước 3 (MediaGateway)

pom có spring-cloud-starter-gateway-server-webflux (tên mới 2025.1), không có spring-boot-starter-web.

Route đúng: /api/users/** → 8081, /api/posts/** → 8082.

CORS dùng CorsWebFilter, allow origin *.

App chạy port 8080, gọi http://localhost:8080/api/users cho kết quả giống gọi thẳng :8081.

Kết quả hiển thị dropdownlist, "Select a definition" với 2 mục user-service / post-service: kết quả này đúng.

Đảm bảo cả UserService (8081) và PostService (8082) đang chạy — vì trình duyệt (không phải gateway) trực tiếp fetch /v3/api-docs từ từng port đó để hiển thị; service nào tắt thì definition đó lỗi/không load được.

Checklist nộp bài

Với mỗi project (UserService, PostService, MediaGateway): chạy mvn clean để xóa thư mục target/. (rất quan trọng vì thư mục này có thể lớn hơn 40MB so với kích thước nộp file trên PEA)

Chuột phải folder project Code PE chứa 3 project trên→ Send to → Compressed (zipped) folder.

Nộp file nén trên lên PEA.

Chúc các em chuẩn bị bài, thi đạt kết quả tốt 😊, cô bichtra!
