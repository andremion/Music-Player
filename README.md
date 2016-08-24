[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Music--Player-green.svg?style=true)](https://android-arsenal.com/details/3/3855)
[![License Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=true)](http://www.apache.org/licenses/LICENSE-2.0)
![minSdkVersion 21](https://img.shields.io/badge/minSdkVersion-21-red.svg?style=true)
![compileSdkVersion 24](https://img.shields.io/badge/compileSdkVersion-24-yellow.svg?style=true)
# Music Player: From UI Proposal to Code

[Transition](https://dribbble.com/shots/1850527-Music-Player-Transition) by [Anish Chandran](https://dribbble.com/anish_chandran) | Code by [André Mion](https://github.com/andremion)
--- | --- | ---
![Transition by Anish Chandran](https://raw.githubusercontent.com/andremion/Music-Player/master/art/music_player_concept_cropped.gif) | ![Code by André Mion](https://raw.githubusercontent.com/andremion/Music-Player/master/art/music_player_code.gif)

![Icon](https://raw.githubusercontent.com/andremion/Music-Player/master/app/src/main/res/mipmap-hdpi/ic_launcher.png)

Read step by step how it was made at https://medium.com/@andremion/music-player-3a85864d6df7#.iklz50r6n

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
