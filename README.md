# Alltomorrows

## Desktop beta prototype

Соберите главное меню первой части:

```bash
./scripts/build.sh
```

Запустите:

```bash
java -jar build/alltomorrows-part1-beta.jar
```

Это скачиваемый desktop-прототип на Java, не web-игра.
Для запуска окна нужна обычная desktop-среда с графическим дисплеем.
Если вы работаете в удалённом терминале без GUI, используйте проверку:

```bash
java -jar build/alltomorrows-part1-beta.jar --smoke-test
```

### Запуск из VS Code

1. Откройте папку проекта в VS Code.
2. Установите расширение **Extension Pack for Java**.
3. Откройте **Run and Debug**.
4. Выберите **Run Part I Beta Menu**.

Для проверки без графического окна можно выбрать **Smoke Test Part I Beta Menu**.
Эти конфигурации запускают собранный `.jar`, поэтому проекту не нужен Maven или Gradle.

## 3D model viewer

Откройте `qu-viewer.html` в браузере, чтобы посмотреть интерактивную
стилизованную fan-made 3D модель Qu без установки Blender.
