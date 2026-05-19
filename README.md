# Snake KMP

Mini game da cobrinha com **Kotlin Multiplatform** e **Compose Multiplatform** para Android e iOS.

## Estrutura

- `composeApp`: módulo compartilhado com lógica e UI do jogo.
  - `commonMain`: estado, regras do jogo e interface Compose.
  - `androidMain`: `MainActivity` e manifesto Android.
  - `iosMain`: `MainViewController` para integração com SwiftUI.

## Requisitos

- JDK 17
- Android SDK (compileSdk 34)

## Build

```bash
./gradlew :composeApp:assembleDebug
```

## iOS

O módulo exporta `MainViewController()` em `iosMain`, que pode ser usado em um app SwiftUI/UIViewControllerRepresentable.
