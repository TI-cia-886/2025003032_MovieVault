# MovieVault - 电影收藏管理

一个使用 **Kotlin + Jetpack Compose + Material 3** 构建的 Android 电影收藏管理应用。支持在线搜索电影、添加到本地收藏、管理观看状态、撰写影评、收藏切换等多种功能。

---

## 功能

### 电影收藏管理
- **在线搜索电影** — 通过 OMDb API 搜索全球电影数据库
- **本地收藏管理** — 将电影添加到本地 Room 数据库，离线可访问
- **观看状态追踪** — 标记电影为"想看 / 正在看 / 已看"
- **用户评分** — 为收藏的电影打星评分 (0.0–5.0)
- **影评系统** — 撰写、查看、删除影评，支持星级评分
- **收藏夹** — 一键标记/取消收藏，快速筛选

### 浏览与交互
- **网格/列表视图切换** — 偏好自动保存
- **多维度排序** — 按日期、IMDb 评分、用户评分排序
- **搜索与筛选** — 本地搜索（标题/导演/演员），按观看状态/收藏筛选
- **深浅色主题** — System / Light / Dark 三态循环切换

### 动画与细节
- Splash 启动页 — 旋转胶卷、闪烁星星、浮动场记板动画
- 空状态/加载状态 — 浮动图标 + 脉冲动画
- 搜索结果卡片 — 海报缩略图 + 添加按钮

---

## 技术栈

| 类别 | 技术 |
|------|------|
| 语言 | Kotlin 2.0.21 |
| UI | Jetpack Compose + Material 3 |
| 架构 | MVVM + Repository 模式 |
| 数据库 | Room (2张表：movies, reviews) |
| 偏好存储 | DataStore Preferences |
| 网络 | Retrofit 2 + OkHttp 4 + Gson |
| 图片加载 | Coil Compose |
| 导航 | Navigation Compose |
| 异步 | Kotlin Coroutines + StateFlow |
| DI | 手动依赖注入 (AppContainer) |

---

## 项目结构

```
MovieVault/
├── app/src/main/java/com/movievault/app/
│   ├── MainActivity.kt              # 主 Activity
│   ├── MovieVaultApp.kt             # Application 类（初始化 AppContainer）
│   ├── AppContainer.kt              # 手动 DI 容器
│   ├── navigation/
│   │   └── AppNavigation.kt         # Navigation Compose 路由定义
│   ├── viewmodel/
│   │   ├── MovieListViewModel.kt    # 列表页 ViewModel
│   │   ├── MovieListUiState.kt      # 列表 UiState (sealed interface)
│   │   ├── MovieDetailViewModel.kt  # 详情页 ViewModel
│   │   ├── MovieDetailUiState.kt    # 详情 UiState
│   │   ├── AddMovieViewModel.kt     # 搜索添加 ViewModel
│   │   └── AddMovieUiState.kt       # 搜索添加 UiState
│   ├── data/
│   │   ├── entity/
│   │   │   ├── MovieEntity.kt       # 电影表实体
│   │   │   └── ReviewEntity.kt      # 影评表实体（外键关联电影）
│   │   ├── dao/
│   │   │   ├── MovieDao.kt          # 电影 DAO
│   │   │   └── ReviewDao.kt         # 影评 DAO
│   │   ├── database/
│   │   │   └── AppDatabase.kt       # Room 数据库（单例）
│   │   ├── repository/
│   │   │   └── MovieRepository.kt   # 数据仓库（统一本地+网络）
│   │   └── network/
│   │       ├── ApiService.kt        # Retrofit API 接口 (OMDb)
│   │       ├── NetworkDataSource.kt # 网络数据源（含 Mock 数据）
│   │       └── dto/MovieDto.kt      # 网络响应 DTO
│   ├── datastore/
│   │   └── UserPreferencesRepository.kt  # DataStore 偏好存储
│   └── ui/
│       ├── screens/
│       │   ├── LoginScreen.kt       # Splash 启动页
│       │   ├── MovieListScreen.kt   # 电影列表主页
│       │   ├── MovieDetailScreen.kt # 电影详情页
│       │   └── AddMovieScreen.kt    # 搜索添加页
│       ├── components/
│       │   ├── EmptyState.kt        # 空/错误/加载状态组件
│       │   └── MovieCard.kt         # 电影卡片组件
│       └── theme/
│           ├── Color.kt             # 颜色定义
│           ├── Theme.kt             # 主题配置
│           └── Type.kt              # 字体排版
```

---

## 运行环境

| 项目 | 版本 |
|------|------|
| Android Studio | 2026.1.1+ |
| Gradle | 8.9 |
| AGP | 8.7.3 |
| minSdk | 26 (Android 8.0) |
| targetSdk | 35 (Android 15) |
| Kotlin | 2.0.21 |
| Java | 17 |

---

## 运行步骤

1. 使用 **Android Studio** 打开项目根目录
2. 等待 **Gradle 同步**完成
3. **API Key 配置**（可选）：
   - 打开 `app/src/main/java/com/movievault/app/data/network/ApiService.kt`
   - 将 `API_KEY` 替换为你自己的 [OMDb API Key](https://www.omdbapi.com/apikey.aspx)
   - 如不配置，可使用内置 Mock 数据（将 `NetworkDataSource.kt` 中 `useMockData` 改为 `true`）
4. 连接**模拟器或真机**，点击 **Run** 运行

---

## 页面说明

| 页面 | 路由 | 功能 |
|------|------|------|
| **Splash** | `splash` | 启动页，展示动画后进入列表 |
| **Movie List** | `movie_list` | 电影收藏列表，网格/列表视图、排序、筛选、搜索 |
| **Movie Detail** | `movie_detail/{movieId}` | 电影详情、观看状态、评分、影评管理 |
| **Add Movie** | `add_movie` | 在线搜索电影并添加到收藏 |

---

## 数据流

```
UI (Compose) → ViewModel (StateFlow) → Repository → Room DAO / NetworkDataSource
                                                     ↓            ↓
                                                本地数据库      OMDb API
```

- ViewModel 通过 `combine` + `flatMapLatest` 实现筛选/搜索/排序的响应式联动
- 搜索防抖：列表搜索 300ms，在线搜索 500ms
- 所有用户偏好（主题、视图模式、排序）通过 DataStore 持久化
- 网络数据源内置 12 部经典电影的完整 Mock 数据

---

## 数据库设计

### movies 表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | INTEGER (PK) | 自增主键 |
| title | TEXT | 电影标题 |
| year | TEXT | 上映年份 |
| posterUrl | TEXT | 海报 URL |
| plot | TEXT | 剧情简介 |
| director | TEXT | 导演 |
| actors | TEXT | 演员 |
| genre | TEXT | 类型 |
| imdbRating | TEXT | IMDb 评分 |
| runtime | TEXT | 片长 |
| imdbId | TEXT | IMDb ID（唯一标识） |
| isFavorite | INTEGER | 是否收藏 (0/1) |
| watchStatus | TEXT | 观看状态 |
| userRating | REAL | 用户评分 (0.0–5.0) |
| createdAt | INTEGER | 创建时间戳 |
| updatedAt | INTEGER | 更新时间戳 |

### reviews 表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | INTEGER (PK) | 自增主键 |
| movieId | INTEGER (FK) | 关联电影（级联删除） |
| content | TEXT | 影评内容 |
| rating | REAL | 影评评分 |
| createdAt | INTEGER | 创建时间戳 |
| updatedAt | INTEGER | 更新时间戳 |

