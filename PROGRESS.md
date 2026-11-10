# 开发记录

## 2026-11-03

### 已有代码

- 完成项目初始化、版本对象封装与正式版本筛选。
- 完成本地 JSON 文件读取与基础异常处理。
- 编写 GitHub Release 请求、JSON 解析和对象转换。

### 本轮学习

- HTTP 请求地址、方法、请求头、状态码与响应正文。
- try/catch、throw/throws 和资源自动关闭。
- Gson 反序列化与外部数据到内部对象的字段映射。

### 待验证

- 记录 GitHub 请求返回 200 时的实际输出。
- 验证不存在仓库、网络失败和 JSON 格式错误时的提示。

### 下一个小目标

从命令行参数指定仓库，避免每次修改源码。

## 后续记录格式

每完成一个小目标，补充：完成内容、如何验证、遇到的问题、下一步。

## 2026-11-04：真实数据与启动参数验证

### 用户实际运行记录

- 参数：google/gson；HTTP 状态码：200。
- 获取三条 Release，筛选后均为正式版本。
- 版本标签：gson-parent-2.15.0、gson-parent-2.14.0、gson-parent-2.13.2。
- 成功展示仓库名、版本标签和原始更新说明。

### 当前源码编译与参数验证

- 使用 JDK 21 与现有 Gson 依赖重新编译当前源码：通过。
- 错误格式参数 google：提示格式错误且不发送请求，验证结果：True。
- 不传参数：请求地址使用默认仓库 spring-projects/spring-ai，验证结果：True。
- 默认仓库网络请求返回 200，验证结果：True。

### 默认仓库本次运行摘要

```text
正在请求：https://api.github.com/repos/spring-projects/spring-ai/releases?per_page=3
HTTP 状态码：200
本次获取 3 条 Release
其中正式版本 1 条
```

### 下一步

为 Release 增加外部 ID、原始链接和发布时间。不存在仓库、网络故障和本地 JSON 错误的完整验证仍待补充。
## 2026-11-04：拆分解析与筛选职责

- 新增 ReleaseParser.parseGitHub：将 GitHub JSON 转换为 Release 列表。
- 新增 ReleaseFilter.onlyStable：返回正式版本列表，不修改原列表。
- GitHubDemo 保留请求和流程组织，复用 Main.printRelease 展示。
- 同步 README 的启动参数、字段补齐与代码结构说明。

### 验证

- 全部 Java 源码编译通过。
- 临时断言检查通过：七个字段映射、空更新说明回退、正式版本筛选、原列表保留、空列表、全预发布列表、无效 JSON、null 数组与 null 元素拒绝。
- Main 本地 JSON 示例：仍展示两个正式版本。
- GitHubDemo google/gson 真实请求与三条数据解析验证：True。
- 本轮使用临时检查程序，没有向项目添加正式 JUnit 测试文件；后续将补充可持续运行的测试。

### 下一步

为解析与筛选添加 JUnit 测试，再逐步拆分参数校验和网络请求。

## 2026-11-10：拆分 GitHub 网络请求客户端

- 新增 GitHubReleaseClient.fetchReleases(repository)，封装 URL、请求头、连接与请求超时、请求发送及状态码检查。
- 客户端成功时返回原始 JSON；非 200 响应转为 IOException，包含状态码和响应正文，由入口显示错误。
- GitHubDemo 保留参数校验、调用解析与筛选、结果展示和异常处理。
- 每次调用中的 HttpClient 仍使用 try-with-resources 自动关闭，保持原有资源管理方式。
- README 已同步代码职责与任务状态。

### 验证结果

- Maven test：6 个测试全部通过，0 失败、0 错误。
- GitHubDemo google/gson：真实请求返回 200，解析并展示 3 条正式版本。
- 不存在仓库：返回 404，客户端抛出异常，入口展示带状态码的失败原因。
- Main 本地 JSON 示例仍展示两个正式版本。

### 后续方向

继续讲解参数校验和客户端的职责，再决定后续改动。
