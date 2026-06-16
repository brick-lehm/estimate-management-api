# ==============================================================================
# Dockerfile — xium-boot Spring Boot テンプレート
#
# FROM を xiumjp/xium-java:21 に変更するだけで Vault 注入が有効になる。
#
# ローカルビルド:
#   mvn clean package -DskipTests
#   docker build -t xiumjp/xium-boot:local .
#
# ローカル実行（Vault なし）:
#   docker run --env-file .env -p 8080:8080 xiumjp/xium-boot:local
#
# Docker Hub push:
#   cd deploy && ./docker_deploy.sh
# ==============================================================================
FROM xiumjp/xium-java:21

WORKDIR /app

COPY ./target/app.jar app.jar

EXPOSE 8080

# ENTRYPOINT は xiumjp/xium-java:21 に定義済み（vault-entrypoint）
# VAULT_ROLE / VAULT_KEYS が設定されている場合は Vault からシークレットを注入して起動
# VAULT_ROLE が未設定の場合は直接 Java プロセスを起動（ローカル / dev 環境）
CMD ["java", "-jar", "app.jar"]
