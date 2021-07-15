# board 게시판 만들기

## 기술스택 
- intelliJ
- JDK8
- springboot2.5.2
- h2
- JPA
- bootstrap

## 기능
- 회원관리
- 게시글 작성
- 게시글 수정
- 게시글 조회
- 파일 업로드
- 화면 디자인

## 1.프로젝트 생성 
  * spring initializr에 접속
  * gradle 프로젝트 선택
  * spring boot 2.5.2 선택
  * 이름을 정한 후 
  * jar, jdk 버전 선택 후 다운로드
  * 압축 해제 후 intelli J 로 불러오기


## 2.H2 데이터 베이스 연동

  * resource/application.properties에 아래 코드로 포트 설정과 데이터베이스 연결
  
  ### server.port=8080  : 포트 번호 
  ### spring.h2.console.enabled=true
  ### spring.datasource.driver-class-name=org.h2.Driver
  ### spring.datasource.url=jdbc:h2:mem:testdb
  ### spring.datasource.username=sa  : 사용자 이름
  ### spring.datasource.password=   : 패스워드
  ### spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
   
   
## 3.컨트롤러 생성

* HomeController를 생성하여 
'''
@Controller //컨트롤러 어노테이션
public class HomeController {

    @GetMapping("/index") //브라우저 매핑
    public String index() {
        return "index";
    }
}
'''
   
