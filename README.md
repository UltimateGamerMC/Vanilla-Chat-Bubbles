# Chat Bubbles

A simple, vanilla-style client-side mod that displays chat messages as floating text bubbles above players' heads.

## Features

- **Vanilla-looking design** - Clean floating text that matches Minecraft's aesthetic
- **Smooth animations** - Bubbles fade in, gently float upward, and fade out
- **Fully client-side** - No server-side requirements
- **Highly customizable** - Easy-to-use config screen via Mod Menu
- **Lightweight** - Minimal performance impact

## Requirements

- **Fabric API** (required)
- **Cloth Config** (required for configuration)
- **Mod Menu** (required for in-game config screen)

## Configuration

Access the config screen through **Mod Menu** (Mods → Chat Bubbles → Config button) or edit `config/chat-bubbles.json` directly.

### Available Options

**Appearance:**
- **Show Player Name** (default: false) - Include player name with the message
- **Text Color** (default: white) - Color picker for text
- **Text Scale** (default: 0.025) - Size of the text
- **Background Color** (default: black) - Color picker for background
- **Background Transparency** (default: 85%) - How transparent the background is

**Animation:**
- **Bubble Height** (default: 0.75 blocks) - Starting height above player's head
- **Upward Speed** (default: 0.1 blocks/sec) - How fast bubbles float upward
- **Display Duration** (default: 3000ms) - How long bubbles stay visible
- **Fade In Duration** (default: 200ms) - Fade-in animation speed
- **Fade Out Duration** (default: 1000ms) - Fade-out animation speed

**Performance:**
- **Max Distance** (default: 64 blocks) - Maximum render distance for bubbles

## Usage

Simply install the mod and chat bubbles will appear automatically when players send chat messages. The default settings are designed to be subtle and vanilla-friendly, with a mostly-transparent background and slow upward drift.
