<img alt="Icon" src="app/src/main/res/mipmap-xxhdpi/ic_launcher.png?raw=true" align="left" hspace="1" vspace="1">

<a alt='Buy Me a Coffee at ko-fi.com' href='https://ko-fi.com/T6T05M4O' target='_blank' align='right'><img align='right' height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi4.png?v=0' border='0' /></a>
<a alt='Try it on Google Play' href='https://play.google.com/store/apps/details?id=com.sample.andremion.musicplayer' target='_blank' align='right'><img align='right' height='36' style='border:0px;height:36px;' src='https://developer.android.com/images/brand/en_generic_rgb_wo_60.png' border='0' /></a>

# Music Player: From UI Proposal to Code

> This is a prototype made for the [article](https://medium.com/@andremion/music-player-3a85864d6df7#.iklz50r6n). This is not a real music player and don't expect it is.

Some developers have difficult to code when the UI proposal is a bit “sophisticated” or “complex”. Many of them strip a lot of significant portion of the UI or even the Motion when they are coding, and the result ends up quite different of the original proposal.

This article talks about how would be to code an UI proposal, skipping some basic Android details and focusing on transition and animation approach...

Read more at [here](https://medium.com/@andremion/music-player-3a85864d6df7#.iklz50r6n)

#### Special thanks to [michaelizer](https://github.com/michaelizer) for the awesome logo.

</br>

[![License Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=true)](http://www.apache.org/licenses/LICENSE-2.0)
![minSdkVersion 21](https://img.shields.io/badge/minSdkVersion-21-red.svg?style=true)
![compileSdkVersion 27](https://img.shields.io/badge/compileSdkVersion-27-yellow.svg?style=true)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Music--Player-green.svg?style=true)](https://android-arsenal.com/details/3/3855)
[![MaterialUp Music-Player](https://img.shields.io/badge/MaterialUp-Music--Player-blue.svg?style=true)](https://www.uplabs.com/posts/music-player-open-source-apps)
[![Android Sweets #27](https://img.shields.io/badge/Android%20Sweets-%2327-ff69b4.svg?style=true)](https://androidsweets.ongoodbits.com/2016/07/14/issue-27)
[![Awesome Android #22](https://img.shields.io/badge/Awesome%20Android-%2322-green.svg?style=true)](https://android.libhunt.com/newsletter/22)

[Transition](https://dribbble.com/shots/1850527-Music-Player-Transition) by [Anish Chandran](https://dribbble.com/anish_chandran) | Code by [André Mion](https://github.com/andremion)
--- | ---
![Transition by Anish Chandran](https://raw.githubusercontent.com/andremion/Music-Player/master/art/music_player_concept_cropped.gif) | ![Code by André Mion](https://raw.githubusercontent.com/andremion/Music-Player/master/art/music_player_code.gif)

## Libraries and tools used in the project

* [Design Support Library](http://developer.android.com/intl/pt-br/tools/support-library/features.html#design)
The Design package provides APIs to support adding material design components and patterns to your apps.
* [MusicCoverView](https://github.com/andremion/Music-Cover-View)
A Subclass of ImageView that 'morphs' into a circle shape and can rotates. Useful to be used as album cover in Music apps.
* [RecyclerView](http://developer.android.com/intl/pt-br/reference/android/support/v7/widget/RecyclerView.html)
A flexible view for providing a limited window into a large data set.
* [PercentRelativeLayout](https://developer.android.com/reference/android/support/percent/PercentRelativeLayout.html)
Subclass of RelativeLayout that supports percentage based dimensions and margins.

## License

    Copyright 2016 André Mion

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
