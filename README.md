# Release Brief

一个逐步开发中的 Java GitHub 仓库版本更新简报工具。

当前阶段：命令行原型。目标是逐步实现仓库订阅、Release 采集、数据去重与 AI 中文摘要。

## 已实现

- 读取本地 JSON，并转换为 Release 对象。
- 使用 Java HttpClient 请求 GitHub Release 列表。
- 将 GitHub 返回字段转换为项目中的 Release 对象。
- 筛选正式版本并打印版本信息。
- 处理文件读取、网络请求、线程中断和 JSON 解析异常。

## 技术栈

Java 21、Maven、Gson、GitHub REST API。

## 如何运行

1. 安装 JDK 21，用 IntelliJ IDEA 打开项目。
2. 将 pom.xml 加载为 Maven 项目，等待依赖下载。
3. 运行 src/main/java/Main.java：读取项目根目录的 releases.json。
4. 运行 src/main/java/GitHubDemo.java：获取 GitHub 上的真实 Release。

本地文件示例的工作目录应为项目根目录。网络示例需要能够访问 api.github.com。

默认请求仓库为 spring-projects/spring-ai，可在 GitHubDemo 的程序参数中填写 google/gson 等 owner/repo 来指定仓库。每次只请求一页、最多三条 Release，因此筛选后可能没有正式版本。

## 代码结构

- Main.java：本地数据读取与信息打印。
- Release.java：项目内部使用的版本对象。
- GitHubDemo.java：处理启动参数，组织请求、解析、筛选和展示流程。
- GitHubReleaseClient.java：构造并发送 HTTP 请求，检查状态码，返回原始 JSON；失败时抛出异常。
- ReleaseParser.java：解析 GitHub JSON 并返回 Release 列表。
- ReleaseFilter.java：筛选正式版本，保留输入列表。
- GitHubRelease.java：接收 GitHub 字段并转换为 Release。
- releases.json：本地模拟数据。

## 开发计划

- [x] Java 项目初始化与 Git 提交
- [x] 本地 JSON 读取与版本筛选
- [x] GitHub Release 请求与对象转换代码
- [ ] 整理真实接口运行结果与异常验证记录
- [x] 从命令行参数指定仓库
- [x] 保留 Release ID、原始链接与发布时间
- [x] 拆分 GitHub JSON 解析和正式版本筛选
- [x] 拆分 GitHub 网络请求客户端
- [ ] 进一步拆分展示逻辑
- [ ] 接入 Spring Boot 与 MySQL
- [ ] 实现去重、任务状态与有限重试
- [ ] 接入 AI 中文摘要并保留来源

仅将已实现的代码标记为完成；部署、数据库、定时任务和 AI 摘要仍在计划中。

## 开发记录

见 PROGRESS.md，持续记录每个小目标、验证结果和遇到的问题。
