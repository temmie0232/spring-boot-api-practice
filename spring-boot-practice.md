# Spring Boot API開発 100本ノック

## フェーズ1: APIの基礎とSpring Bootの基本 (Knock 1-20)
*目的: HTTPの基本を理解し、Spring Bootで簡単なAPIの作成・応答ができるようになる。データベースはまだ使わない。*

### 環境構築とHello World
1.  **お題**: Spring Initializrでプロジェクトを作成し、`GET /hello`で`"Hello, World!"`と返すAPIを作る。
    * **キーワード**: `Spring Initializr`, `Spring Web`, `@RestController`, `@GetMapping`
2.  **お題**: `GET /goodbye`で`"Goodbye!"`と返すAPIを追加する。
    * **キーワード**: Controllerにメソッドを追加

### リクエストの多様な受け取り方
3.  **お題**: `GET /greet/{name}`のようにURLの一部を可変値(パス変数)として受け取り、`"Hello, {name}!"`と返す。
    * **キーワード**: `@PathVariable`
4.  **お題**: `GET /add?a=3&b=5`のようにURLのクエリパラメータを受け取り、その合計値(`8`)を返す。
    * **キーワード**: `@RequestParam`
5.  **お題**: クエリパラメータ`b`が省略された場合、`b`を`0`として計算するように修正する。
    * **キーワード**: `@RequestParam(required = false, defaultValue = "0")`

### JSONの扱い
6.  **お題**: `GET /me`で、自身のプロフィール情報をJSONで返す。JSONは`{"name": "your-name", "age": 25}`のような形式。
    * **キーワード**: DTO (Data Transfer Object), `class`, `new`
7.  **お題**: `GET /users`で、複数のユーザー情報リストをJSON配列で返す。
    * **キーワード**: `List<User>`, `ArrayList`
8.  **お題**: `POST /users`で、リクエストボディのJSON (`{"name": "Sato", "age": 30}`) を受け取り、 `"User Sato created."`という文字列を返す。
    * **キーワード**: `@PostMapping`, `@RequestBody`

### サービス層の導入と責務の分離
9.  **お題**: ユーザー管理のロジックを`UserService`クラスに分離する。ControllerはServiceを呼び出すだけにする。
    * **キーワード**: `@Service`, `@Autowired`, DI (Dependency Injection)
10. **お題**: `UserService`内でユーザーリストをメモリ上(`List<User>`)に保持し、`POST`されたユーザーを追加できるようにする。
    * **キーワード**: `ArrayList`, `add`
11. **お題**: `GET /users/{id}`で、メモリ上のリストから特定のIDのユーザーを検索して返す。
    * **キーワード**: Stream API, `filter`, `findFirst`
12. **お題**: IDが見つからなかった場合に、HTTPステータスコード`404 Not Found`を返す。
    * **キーワード**: `ResponseEntity`, `HttpStatus.NOT_FOUND`
13. **お題**: ユーザー作成成功時(`POST /users`)に、ステータスコード`201 Created`を返すように修正する。
    * **キーワード**: `ResponseEntity.created()`, `URI`

### 更新と削除 (PUT/DELETE)
14. **お題**: `PUT /users/{id}`で、指定したIDのユーザー情報をリクエストボディのJSONで更新する。
    * **キーワード**: `@PutMapping`
15. **お題**: `DELETE /users/{id}`で、指定したIDのユーザーをメモリ上のリストから削除する。
    * **キーワード**: `@DeleteMapping`, `removeIf`
16. **お題**: ユーザー削除成功時に、ステータスコード`204 No Content`を返すように修正する。
    * **キーワード**: `ResponseEntity.noContent()`

### 設定とその他
17. **お題**: サーバーのポート番号を`8080`から`8888`に変更する。
    * **キーワード**: `application.properties`, `server.port`
18. **お題**: `PATCH /users/{id}/name`で、ユーザーの名前だけを更新するAPIを作る。
    * **キーワード**: `@PatchMapping`
19. **お題**: `GET /header-info`で、リクエストヘッダーの`User-Agent`を取得して返す。
    * **キーワード**: `@RequestHeader`
20. **お題**: 全てのAPIのエンドポイントの前に `/api` というプレフィックスを付ける。(例: `/api/users`)
    * **キーワード**: `@RequestMapping("/api")` on Controller class

## フェーズ2: データベース連携とJPA (Knock 21-45)
*目的: H2/MySQL等のデータベースと連携し、永続的なデータ操作(CRUD)をマスターする。*

### Spring Data JPA と H2データベース
21. **お題**: `spring-boot-starter-data-jpa`と`h2database`の依存関係を追加する。
    * **キーワード**: `pom.xml` or `build.gradle`, Maven/Gradle dependencies
22. **お題**: ユーザー情報を格納する`User`クラスを、DBのテーブルに対応するエンティティクラスに変更する。
    * **キーワード**: `@Entity`, `@Id`, `@GeneratedValue`
23. **お題**: `JpaRepository`を継承した`UserRepository`インターフェースを作成する。
    * **キーワード**: `JpaRepository`
24. **お題**: `UserService`を修正し、メモリ上のリストではなく`UserRepository`を使ってデータを操作するように変更する。
    * **キーワード**: `repository.save()`, `repository.findAll()`
25. **お題**: `findById`メソッドを使い、`Optional<User>`を正しくハンドリングしてユーザーを検索する。
    * **キーワード**: `repository.findById()`, `Optional`, `orElseThrow`
26. **お題**: `deleteById`メソッドを使ってユーザーを削除する。
    * **キーワード**: `repository.deleteById()`
27. **お題**: アプリケーション起動時に、`data.sql`を使って初期データ(ユーザー3名分)が投入されるようにする。
    * **キーワード**: `src/main/resources/data.sql`
28. **お題**: H2のコンソールを有効にし、ブラウザからDBの状態を確認できるようにする。
    * **キーワード**: `spring.h2.console.enabled=true`

### リレーションシップ
29. **お題**: 新たに「投稿(Post)」エンティティを作成する。`Post`は`title`と`content`を持つ。
    * **キーワード**: `@Entity`
30. **お題**: `User`と`Post`の間に「1対多」のリレーションを定義する(1人のユーザーが複数の投稿を持つ)。
    * **キーワード**: `@OneToMany`, `@ManyToOne`
31. **お題**: `POST /users/{userId}/posts`で、特定のユーザーに紐づく投稿を作成するAPIを作る。
    * **キーワード**: PathVariable, `user.getPosts().add(post)`
32. **お題**: `GET /users/{userId}/posts`で、特定のユーザーの全投稿リストを取得するAPIを作る。
    * **キーワード**: `LazyInitializationException` and how to solve it
33. **お題**: N+1問題を意図的に発生させ、`JOIN FETCH`を使って解決する。
    * **キーワード**: N+1 problem, `@Query`, `JOIN FETCH`

### 高度なクエリ
34. **お題**: `UserRepository`に、名前でユーザーを検索するメソッド`findByName`を追加する。
    * **キーワード**: Derived Query Methods
35. **お題**: `PostRepository`に、特定のキーワードを`title`に含む投稿を検索する`findByTitleContaining`メソッドを追加する。
    * **キーワード**: `Containing` keyword
36. **お題**: `UserRepository`に、JPQLを使って指定した年齢以上のユーザーを検索するメソッドを実装する。
    * **キーワード**: `@Query`, JPQL
37. **お題**: `PostRepository`に、ネイティブSQLを使って投稿を全件削除するメソッドを実装する。
    * **キーワード**: `@Query(nativeQuery = true)`, `@Modifying`

### データ移行とDTO
38. **お題**: H2データベースから、ローカルのMySQL/PostgreSQLに接続先を変更する。
    * **キーワード**: `application.properties`, `spring.datasource.url`, JDBC Driver
39. **お題**: DBのスキーマを自動生成・更新するために`ddl-auto`を設定する。
    * **キーワード**: `spring.jpa.hibernate.ddl-auto`
40. **お題**: ユーザー作成用の`UserCreateRequest` DTOと、ユーザー情報応答用の`UserResponse` DTOを導入する。
    * **キーワード**: DTO pattern, Entity exposure
41. **お題**: ControllerではDTOのみを扱い、Service層でEntityとDTOの変換を行うようにリファクタリングする。
    * **キーワード**: Mapper, ModelMapper, MapStruct
42. **お題**: 投稿(Post)についても、`PostResponse` DTOを導入し、ユーザー情報を含めて返すようにする。
    * **キーワード**: Nested DTOs
43. **お題**: データベースのトランザクション管理について調べ、`@Transactional`アノテーションの役割を理解する。
    * **キーワード**: `@Transactional`, `readOnly=true`
44. **お題**: 楽観的ロックを用いて、同時に同じ投稿を更新しようとした場合にエラーを発生させる。
    * **キーワード**: `@Version`, `OptimisticLocking`
45. **お題**: データベースのインデックスについて調べ、`User`エンティティの`name`カラムにインデックスを設定する。
    * **キーワード**: `@Index`, `@Table`

## フェーズ3: 実践的なAPI開発 (Knock 46-75)
*目的: バリデーション、エラーハンドリング、テストなど、本番環境で必須となる周辺技術を習得する。*

### バリデーション
46. **お題**: ユーザー作成時、名前が空(`null` or `""`)の場合はエラーにする。
    * **キーワード**: `spring-boot-starter-validation`, `@NotNull`, `@NotEmpty`
47. **お題**: ユーザー作成時、名前に文字数制限(2~20文字)を設ける。
    * **キーワード**: `@Size`
48. **お題**: バリデーションエラーが発生した際に、HTTPステータス`400 Bad Request`とエラー内容が返るようにする。
    * **キーワード**: `@Valid`, `MethodArgumentNotValidException`
49. **お題**: 年齢が18歳未満の場合は登録できないように、独自のバリデーション(カスタムアノテーション)を作成する。
    * **キーワード**: Custom Validator, `@Constraint`
50. **お題**: 更新(`PUT`)時と作成(`POST`)時で異なるバリデーションルールを適用する。
    * **キーワード**: Validation Groups

### 例外処理
51. **お題**: `UserService`でユーザーが見つからない場合に、独自の`UserNotFoundException`をスローするように変更する。
    * **キーワード**: `extends RuntimeException`
52. **お題**: `@ControllerAdvice`と`@ExceptionHandler`を使い、`UserNotFoundException`を補足して`404`エラーを返す共通処理を実装する。
    * **キーワード**: Global Exception Handler
53. **お題**: エラーレスポンスのJSONフォーマットを独自のもの（例: `{"timestamp": "...", "status": 404, "error": "Not Found", "message": "User not found"}`）に統一する。
    * **キーワード**: Custom Error Response DTO

### ページネーションとソート
54. **お題**: `GET /users` APIにページネーション機能を実装する。(`?page=0&size=5`)
    * **キーワード**: `Pageable`, `Page`
55. **お題**: ユーザー一覧を名前順(`?sort=name,asc`)や年齢順(`?sort=age,desc`)でソートできるようにする。
    * **キーワード**: `Sort`

### テスト
56. **お題**: `UserService`の`findAll`メソッドに対する単体テスト(Unit Test)をJUnitとMockitoで記述する。
    * **キーワード**: `@ExtendWith(MockitoExtension.class)`, `@Mock`, `@InjectMocks`
57. **お題**: ユーザー作成ロジック(`createUser`)の単体テストを記述する。
    * **キーワード**: `Mockito.when()`, `Mockito.verify()`
58. **お題**: `UserController`の`GET /users/{id}` APIに対する結合テスト(Integration Test)を`MockMvc`を使って記述する。
    * **キーワード**: `@SpringBootTest`, `@AutoConfigureMockMvc`, `mockMvc.perform()`
59. **お題**: APIが`404`を正しく返す場合のテストケースを追加する。
    * **キーワード**: `andExpect(status().isNotFound())`
60. **お題**: `POST /users` APIの結合テストを記述し、バリデーションエラーのテストも行う。
    * **キーワード**: `MediaType.APPLICATION_JSON`, `content()`, `jsonPath()`

### 設定とプロファイル
61. **お題**: `dev`(開発)、`prod`(本番)の2つのプロファイルを作成し、`application-{profile}.properties`でDB接続先を切り替えられるようにする。
    * **キーワード**: `spring.profiles.active`
62. **お題**: `@ConfigurationProperties`を使い、独自のアプリケーション設定(例: `app.admin.email`)をタイプセーフに読み込む。
    * **キーワード**: `@ConfigurationProperties`, `@EnableConfigurationProperties`
63. **お題**: `@Value`アノテーションを使って、`application.properties`の値を直接フィールドにインジェクションする。
    * **キーワード**: `@Value("${...}")`

### 非同期処理とスケジューリング
64. **お題**: ユーザー作成後に、時間がかかるであろう「ウェルカムメール送信」処理を非同期で行う。
    * **キーワード**: `@Async`, `@EnableAsync`
65. **お題**: 毎日深夜1時に、特定のバッチ処理(例: `System.out.println("Batch running...")`)を実行するスケジュールタスクを実装する。
    * **キーワード**: `@Scheduled`, `@EnableScheduling`

### ファイル操作
66. **お題**: `POST /users/{id}/avatar`で、ユーザーのアバター画像(ファイル)をアップロードできるAPIを作成する。
    * **キーワード**: `MultipartFile`, `spring.servlet.multipart.max-file-size`
67. **お題**: アップロードされたファイルをサーバーの特定のディレクトリに保存する。
    * **キーワード**: `Path`, `Files.copy`
68. **お題**: `GET /users/{id}/avatar`で、保存されたアバター画像をダウンロードできるAPIを作成する。
    * **キーワード**: `Resource`, `ResponseEntity<Resource>`

### その他
69. **お題**: CORS(Cross-Origin Resource Sharing)を設定し、`http://localhost:3000`(フロントエンド開発でよく使われる)からのアクセスを許可する。
    * **キーワード**: `@CrossOrigin`, `WebMvcConfigurer`
70. **お題**: Spring Boot Actuatorを導入し、`/actuator/health`でアプリケーションのヘルスチェックができるようにする。
    * **キーワード**: `spring-boot-starter-actuator`
71. **お題**: `RestTemplate`または`WebClient`を使い、外部の公開API(例: `https://jsonplaceholder.typicode.com/todos/1`)を呼び出し、その結果を返すAPIを作成する。
    * **キーワード**: `RestTemplate`, `WebClient`
72. **お題**: APIのログ出力を設定し、特定のパッケージ(例: `com.example.apipractice`)のログレベルを`DEBUG`に変更する。
    * **キーワード**: `logging.level.<package-name>`
73. **お題**: Lombokを導入し、DTOやEntityのGetter/Setter、コンストラクタ等の記述を簡略化する。
    * **キーワード**: Lombok, `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`
74. **お題**: APIの各メソッドの実行時間をAOP(Aspect-Oriented Programming)を使ってログ出力する。
    * **キーワード**: `@Aspect`, `@Around`, `ProceedingJoinPoint`
75. **お題**: `springdoc-openapi-ui`を導入し、Swagger UIでAPI仕様書を自動生成・表示する。
    * **キーワード**: Swagger, OpenAPI 3, `@Operation`, `@Parameter`

## フェーズ4: セキュリティと認証 (Knock 76-90)
*目的: Spring Securityを導入し、安全なAPIを構築するための認証・認可の仕組みを実装する。*

### Spring Securityの基本
76. **お題**: `spring-boot-starter-security`を導入し、全てのAPIがデフォルトで認証必須(Basic認証)になることを確認する。
    * **キーワード**: Spring Security, `SecurityFilterChain`
77. **お題**: `POST /users` (ユーザー登録) と Swagger UI関連のエンドポイントは認証なしでアクセスできるように設定を緩和する。
    * **キーワード**: `SecurityFilterChain` bean, `requestMatchers(...).permitAll()`
78. **お題**: デフォルトの`user`ではなく、DBに保存されたユーザー情報(`UserRepository`)を使って認証を行うように設定する。
    * **キーワード**: `UserDetailsService`, `PasswordEncoder`
79. **お題**: パスワードを平文ではなく、`BCryptPasswordEncoder`を使ってハッシュ化してDBに保存する。
    * **キーワード**: `BCryptPasswordEncoder`

### JWT(JSON Web Token)による認証
80. **お題**: `jjwt`ライブラリを導入し、JWTを生成・検証する`JwtProvider`クラスを作成する。
    * **キーワード**: `jjwt-api`, `jjwt-impl`, `jjwt-jackson`
81. **お題**: `POST /login` APIを実装する。リクエストボディのユーザー名とパスワードで認証し、成功したらJWTを返す。
    * **キーワード**: `AuthenticationManager`
82. **お題**: JWTを受け取るための認証フィルタを自作し、Spring Securityのフィルタチェーンに組み込む。
    * **キーワード**: `OncePerRequestFilter`, `addFilterBefore`
83. **お題**: リクエストヘッダー(`Authorization: Bearer <token>`)からJWTを抽出し、トークンを検証して認証情報をコンテキストに設定する。
    * **キーワード**: `SecurityContextHolder`, `UsernamePasswordAuthenticationToken`
84. **お題**: Basic認証を無効化し、JWT認証のみを使用するように設定する。
    * **キーワード**: `httpBasic().disable()`

### 認可と権限管理
85. **お題**: `User`エンティティに`role`フィールド(例: `ROLE_USER`, `ROLE_ADMIN`)を追加する。
    * **キーワード**: `GrantedAuthority`, `SimpleGrantedAuthority`
86. **お題**: ユーザー削除(`DELETE /users/{id}`) APIは、`ADMIN`権限を持つユーザーのみが実行できるようにアクセス制限をかける。
    * **キーワード**: `hasRole('ADMIN')`, `hasAuthority('ROLE_ADMIN')`
87. **お題**: `@PreAuthorize`アノテーションを使い、メソッド単位でより詳細な認可制御を行う。
    * **キーワード**: `@EnableMethodSecurity`, `@PreAuthorize("hasRole('ADMIN')")`
88. **お題**: ログインしているユーザー自身の情報(`GET /me`)を取得するAPIを実装する。
    * **キーワード**: `@AuthenticationPrincipal`
89. **お題**: JWTに含める情報(クレーム)に、ユーザーIDやロール情報を追加する。
    * **キーワード**: JWT claims
90. **お題**: JWTのリフレッシュトークン機能を実装する。
    * **キーワード**: Refresh Token, Access Token

## フェーズ5: コンテナ化とデプロイ (Knock 91-100)
*目的: 作成したアプリケーションをコンテナ化し、デプロイ可能な状態にする。*

### Docker
91. **お題**: Spring Bootアプリケーションをjarファイルとしてビルドする。
    * **キーワード**: `mvn clean package`, `gradle build`
92. **お題**: アプリケーションを動かすための`Dockerfile`を作成する。
    * **キーワード**: `FROM`, `COPY`, `ENTRYPOINT`
93. **お題**: Dockerイメージをビルドし、コンテナとして起動する。
    * **キーワード**: `docker build`, `docker run`
94. **お題**: `docker-compose.yml`を作成し、Spring BootアプリとMySQL/PostgreSQLデータベースを同時に起動できるようにする。
    * **キーワード**: Docker Compose, `depends_on`, `environment`
95. **お題**: DBの接続情報などを、ハードコーディングではなく環境変数から読み込むようにアプリケーションを修正する。
    * **キーワード**: Environment Variables, `System.getenv()`

### CI/CDとクラウド
96. **お題**: GitHub Actionsを使い、リポジトリにpushされたら自動的にテストが実行されるワークフローを構築する。
    * **キーワード**: GitHub Actions, YAML workflow, `actions/setup-java`
97. **お題**: さらに、テストが成功したら自動的にDockerイメージをビルドし、Docker HubやGitHub Container Registryにpushする。
    * **キーワード**: `docker/login-action`, `docker/build-push-action`
98. **お題**: AWS, GCP, Azureなどのクラウドプラットフォームのアカウントを作成し、無料で利用できる範囲を調べる。
    * **キーワード**: AWS Free Tier, GCP Free Tier, Azure Free Account
99. **お題**: クラウド上のマネージドデータベース(RDS, Cloud SQLなど)を作成する。
    * **キーワード**: AWS RDS, GCP Cloud SQL
100. **お題**: **最終課題**: これまで作成したアプリケーションをコンテナ実行サービス(ECS, Cloud Run, App Serviceなど)にデプロイし、公開URLからアクセスできるようにする。
    * **キーワード**: AWS ECS, GCP Cloud Run, Azure App Service, Deployment