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

  1. resource/application.properties에 아래 코드로 포트 설정과 데이터베이스 연결
  
  ```
 server.port=8080  : 포트 번호 
 spring.h2.console.enabled=true
 spring.datasource.driver-class-name=org.h2.Driver
 spring.datasource.url=jdbc:h2:mem:testdb
 spring.datasource.username=sa  : 사용자 이름
 spring.datasource.password=   : 패스워드
 spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
  ```
  2. 연동 후 board 테이블 생성, 
   * id: 게시글 번호
   * title: 게시글 명
   * content: 게시글 내용
   * 컬럼 생성
   
  3. 생성한 컬럼들을 모델 클래스인 Board에 작성  
  ```java
  @Entity //데이터베이스 연동을 위한 모델 클래스
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment를 사용하면 자동으로 게시글 번호가 증가 되도록 해주는 어노테이션
    private Long id;
    private String title;
    private String content;

}
```

## 3. 홈화면으로 접속해보기

* HomeController를 생성하여 index 페이지로 잘 이동하는지 확인.
```java
@Controller //컨트롤러 어노테이션
public class HomeController {

    @GetMapping("/index") //브라우저 매핑
    public String index() {
        return "index";
    }
}
```
![image](https://user-images.githubusercontent.com/84885273/125758483-7673e532-4bb1-466c-b8cf-91911888294d.png)
다행히 index페이지로 이동하였다.

## 4. 게시판 화면 접속하여 총 게시글 출력 
1. 먼저 templates 폴더 아래 board폴더 생성 후 게시판 화면인 list.html을 만들어 주었다.
2. 게시판 화면을 컨트롤 해줄 BoardController 생성 
3. 코드:
```java
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired //Autowired에 의해 서버 기동될 때 인스턴스 들어옴
    private BoardRepository boardRepository;
    //BoardRepository를 이용해서 테이블의 데이터를 가져옴

    @GetMapping("/list")
    public String list(Model model){ //데이터값을 전해주려면 Model을 추가하면됨
        List<Board> boards = boardRepository.findAll(); //boards라는 데이터 가져옴
        model.addAttribute("boards", boards); //모델에 담긴 데이터들은 thymeleaf를 이용해서 사용할 수 있음
        return "board/list";
    }
```
4. 테이블에 저장된 게시글들을 .findAll()을 통해 boards에 저장 
5. list.html에 boards에 저장된 게시글 총 개수를 출력하는 코드를 작성
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Board List</title>
</head>
<body>

<div>
    <h2>게시판</h2>
    <div>총 게시글: <span th:text="${#lists.size(boards)}"></span></div>
    <!-- boards: 게시글 리스트를 전달 받아 출력하는 thymeleaf 문법 -->
</div>

</body>
</html>
```
6. 작성된 게시글이 없어 게시글 2개를 삽입해보았다.

![image](https://user-images.githubusercontent.com/84885273/125760942-f06796c8-eaff-4936-b69f-ef1abf2edb69.png)

![image](https://user-images.githubusercontent.com/84885273/125760848-a365eb58-1ef9-4c2e-9f2e-028b3ecdbda9.png)


   
## 5. 데이터베이스 변경: h2를 쓰려 했으나 그냥 예제에서 쓰는 마리아 DB로 변경하였다.

1. mariadb 검색 후 다운로드 진행
2. heidiSQL 파일 실행
3. ![image](https://user-images.githubusercontent.com/84885273/126042260-cb16e5be-e536-45a8-9cc5-b99cb8257f94.png)
4. ![image](https://user-images.githubusercontent.com/84885273/126042392-334dc9a3-75c4-427d-b5a4-22209428c22a.png)
### 우클릭으로도 쉽게 테이블 생성이 가능하다.

## 6. 데이터베이스 연동

1. 의존성 설정: 데이터베이스 정보와 계정 정보를 입력하여 연동시킨다.
```java
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.datasource.username=myadmin
spring.datasource.password=qwaszx96
spring.datasource.url=jdbc:mariadb://localhost:3306/mydb
```

2. build.gradle에 implementation 추가
```java
   implementation group : 'org.mariadb.jdbc', name: 'mariadb-java-client', version: '2.4.1'
```

3. 연동 확인
![image](https://user-images.githubusercontent.com/84885273/126043873-80fd1c99-41a4-49ec-b5f7-efd66f66e018.png)
![image](https://user-images.githubusercontent.com/84885273/126043877-73f4daaa-66b6-445a-889b-7944b2234ef6.png)

## 7.게시글 작성 기능 추가

1. 컨트롤러 설정: @GetMapping
```java
@GetMapping("/form")
    public String form(Model model){
        model.addAttribute("board", new Board());
        return "board/form";
    }
```

2. form.html 파일 생성 후 제목,내용 입력 칸과  버튼 생성
```html
<div>
    <label for="title">제목</label>
    <input type="text" id="title" placeholder="제목">
</div>
<div>
    <label for="content">내용</label>
    <textarea id="content" placeholder="내용">
</div>
<div>
    <a type="button" th:href="@{/borad/list}">취소</a> <!-- 취소버튼을 누르면 /board/list창으로 이동하도록 설정-->
    <button type="submit">확인</button>
</div>
``` 

3. 작성된 데이터를 보내주기: @PostMapping(포스트 요청)
```java
  @PostMapping("/form")
    public String greetingSubmit(@ModelAttribute Board board){  //@ModelAttribute : board 클래스를 받아올 수 있음
        boardRepository.save(board);  //받은 클래스를 저장
        return "redirect:/board/list";  //작성이 완료되면 list 페이지로 이동
    }
```

4. form 태그로 데이터 전송 받기
```html
<form action="#" th:action="@{/board/form}" th:object="${board}" method="post">  
        <div>
            <label for="title">제목</label>
            <input type="text" id="title" placeholder="제목" th:field="*{title}">
        </div>
        <div>
            <label for="content">내용</label>
            <textarea id="content" placeholder="내용" th:field="*{content}"></textarea>
        </div>
        <div>
            <a type="button" th:href="@{/borad/list}">취소</a>
            <button type="submit">확인</button>
        </div>
    </form>
```

5. 실행 화면
![image](https://user-images.githubusercontent.com/84885273/126044286-38595dce-e378-4a1e-b3e7-13183d01e53c.png)

![image](https://user-images.githubusercontent.com/84885273/126044289-6c86b278-d190-46c3-a5ed-45fb94ced326.png)


