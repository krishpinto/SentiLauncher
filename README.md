
---

# Android Launcher README

```md
# Sentinel Launcher

Sentinel Launcher is the native Android shell for the Sentinel security system. It provides the Android-native layer that complements the main React Native Sentinel app.

This work was part of the broader Sentinel system, which **won a hackathon with 1700+ participants**, demonstrating both technical depth and product relevance.

## Why It Exists

React Native gave us fast product development and polished UX, but Android-native integration was needed for:

- launcher / home screen behavior
- notification listening
- system-level event ingestion
- future telephony hooks

Sentinel Launcher solves that gap by acting as the native entry layer for the product.

## What It Does

### Launcher Shell
- can be selected as the Android home launcher
- gives Sentinel a system-level presence
- opens the main Sentinel app

### Notification Ingestion
- listens for device notifications
- extracts title/text
- forwards content into Sentinel via deep links

### Native Integration Layer
- bridges Android-native services into the React Native Sentinel app
- supports a more realistic passive-protection architecture

### Call Experiments
- includes native Android experiments for:
  - call screening
  - phone-state detection

## Main Files

- **`AndroidManifest.xml`**
  - declares launcher behavior, permissions, and native services

- **`MainActivity.kt`**
  - launcher UI
  - Open Sentinel button
  - test notification trigger
  - call-screening role request

- **`SentinelNotificationListener.kt`**
  - notification listener that forwards content to Sentinel

- **`SentinelCallScreeningService.kt`**
  - native call-screening experiment

- **`SentinelPhoneStateReceiver.kt`**
  - phone-state monitoring experiment

## Deep-Link Bridge

The launcher/native layer passes events into the Sentinel app using:

```text
sentinel://ingest?sourcetype=SMS&label=...&content=...
