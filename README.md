# WhatsApp Call Recorder

An Android app that records WhatsApp calls with automatic consent notification to the other party.

## Features

- 🎙️ **Automatic Recording**: Detects WhatsApp calls and starts recording automatically
- 📢 **Consent Notification**: Notifies the other person that the call is being recorded
- 💾 **Local Storage**: Saves recordings to local device storage
- 🔔 **Real-time Status**: Shows recording status in the UI
- 🛡️ **Permission Handling**: Requests all necessary permissions from the user

## Requirements

- Android API Level 24 (Android 7.0) or higher
- Minimum 100MB free storage for recordings
- WhatsApp installed on device

## Permissions Required

- `RECORD_AUDIO` - To record call audio
- `MODIFY_AUDIO_SETTINGS` - To manage audio settings during recording
- `READ_PHONE_STATE` - To detect call state
- `READ_CALL_LOG` - To access call information
- `WRITE_EXTERNAL_STORAGE` - To save recordings
- `READ_EXTERNAL_STORAGE` - To read recording files
- `QUERY_ALL_PACKAGES` - To detect WhatsApp calls

## Installation

1. Clone the repository:
```bash
git clone https://github.com/rrr132/whatsapp-call-recorder.git
cd whatsapp-call-recorder
```

2. Open in Android Studio

3. Build and run on your device

## How It Works

1. **Call Detection**: The app monitors phone state and WhatsApp call broadcasts
2. **Recording**: When a call is detected, audio recording starts automatically
3. **Consent**: A notification/message is sent to the caller informing them of the recording
4. **Storage**: Recordings are saved to `/Documents/WhatsAppCallRecorder/`
5. **Format**: Recordings are saved in M4A format

## File Structure

```
app/src/main/
├── java/com/example/whatsappcallrecorder/
│   ├── MainActivity.kt              # Main UI activity
│   ├── service/
│   │   └── CallDetectionService.kt  # Service handling recording
│   ├── receiver/
│   │   ├── PhoneStateReceiver.kt    # Phone state detection
│   │   └── WhatsAppCallReceiver.kt  # WhatsApp call detection
│   └── utils/
│       └── NotificationHelper.kt    # Notification utilities
└── res/
    ├── layout/
    │   └── activity_main.xml        # Main UI layout
    ├── values/
    │   └── strings.xml              # String resources
    └── ...
```

## Legal Notice

**Important**: Recording phone calls without consent from all parties may be illegal in your jurisdiction. Please ensure you comply with local laws and regulations. This app includes consent notification functionality to help with legal compliance. Users are responsible for their own legal compliance.

## Troubleshooting

### Recording not starting?
- Ensure all permissions are granted
- Check that WhatsApp is installed
- Verify you have sufficient storage space

### No audio in recording?
- Ensure audio recording permission is granted
- Check device audio settings
- Try using speaker mode

### App crashes on startup?
- Clear app data and cache
- Ensure Android OS is up to date
- Check that you're on API 24 or higher

## Future Improvements

- [ ] Add automatic consent message via WhatsApp API
- [ ] Implement call transcription
- [ ] Add cloud backup for recordings
- [ ] Recording quality settings
- [ ] Call history with duration
- [ ] Playback interface
- [ ] Export recordings

## License

MIT License - See LICENSE file for details

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Disclaimer

This app is provided as-is. The developer is not responsible for misuse or any legal consequences resulting from improper use of this application. Users must ensure compliance with all applicable laws and regulations in their jurisdiction regarding call recording.
