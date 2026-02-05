# MEMORY.md - 长期记忆

## 项目信息
- **项目名称**：棋牌小程序游戏
- **技术栈**：Node.js + Express + PostgreSQL + Redis + Socket.io
- **功能**：支持麻将、斗地主、德州扑克等棋牌游戏

## 数据库迁移
- **原数据库**：MongoDB
- **新数据库**：PostgreSQL
- **迁移时间**：2026-02-04
- **ORM**：从 Mongoose 改为 Sequelize

## 安全配置
- **CSP 策略**：已配置 Content Security Policy，允许加载外部资源
- **HTTPS**：当前使用 HTTP，建议生产环境使用 HTTPS

## 服务器配置
- **Redis**：用于缓存和会话管理
- **PostgreSQL**：用于数据存储
- **端口**：3000

## 待办事项
- 优化游戏功能
- 添加更多游戏类型
- 完善用户体验
- 配置 HTTPS
