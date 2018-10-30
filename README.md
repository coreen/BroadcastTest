# Broadcast Test

Testing broadcast receivers with basic MainActivity and home app Widget.

## Testing

### Activity to Widget

1. Add a home app Widget. Contents should be blank.
2. Open MainActivity.
3. Click the "Click to broadcast" button.
4. Visit home page Widget.
5. Contents should now be populated.

### Widget to Activity

1. Add a home app Widget. Contents should contain 2 buttons with "Play" and "Skip" text.
2. Open LogCat in Android Studio IDE.
3. Click the "Play" button.
4. LogCat should record the WIDGET_PLAY_ACTION was received.
5. Click the "Skip" button.
6. LogCat should record the WIDGET_SKIP_ACTION was received.

