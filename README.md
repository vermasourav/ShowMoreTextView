# ShowMoreTextView
Show more Text View

================================
# Setup
Add it to your build.gradle with:
```
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
and In your Gradle dependencies add:
```
dependencies {
    implementation "com.github.vermasourav:ShowMoreTextView:1.0.11"
}
```
# Define in xml:

```
<com.verma.android.widgets.showmore.ShowMoreTextView 
    android:id="@+id/show_more_text_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="16dp"
    android:text="@string/more_text"
    android:textSize="20sp"
    app:show_more_text="@string/show_more"
    app:show_less_text="@string/show_less"
    app:show_more_color="@android:color/holo_blue_bright"
    app:show_less_color="#FF0000"
    app:show_more_underline="true"
    app:show_more_bold="true"
    app:show_more_animation="true"
    app:show_more_expand_click="true"
    app:show_more_collapse_click="true"
    app:show_more_linkify="true"
    app:show_more_max_line="300"
    app:show_more_text_length_type="TYPE_CHARACTER"
    tools:text="@string/more_text" />
    
```

# Or in code:

================================

You can use the following properties in your XML to change your ShowMoreTextView.

```
Properties	Type	Default
app:show_more_text  string  R.string.show_more ("show more")   
app:show_less_text  string  R.string.show_less ("show less")
app:show_more_color color   R.color.show_more (#FF0000)
app:show_less_color color   R.color.show_less (#FF0000)
app:show_more_underline boolean true
app:show_more_bold  boolean true
app:show_more_animation boolean true
app:show_more_expand_click  boolean true
app:show_more_collapse_click    boolean true
app:show_more_linkify   boolean true 
app:show_more_max_line  Integer MAX_CHARACTER (100)
app:show_more_text_length_type  show_more_text_length_type TYPE_CHARACTER (1)
```
|                 Screen 1               |   Screen 2  |
|----------------------------------------|-------------|
| <img src="/Screenshots/Screenshot_20241130_120635.png" width="250"> | <img src="/Screenshots/Screenshot_20241130_120653.png" width="250"> |

