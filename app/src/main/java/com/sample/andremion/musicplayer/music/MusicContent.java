/*
 * Copyright (c) 2016. André Mion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sample.andremion.musicplayer.music;

import com.sample.andremion.musicplayer.R;

import java.util.ArrayList;
import java.util.List;

public class MusicContent {

    public static final List<MusicItem> ITEMS = new ArrayList<>();

    static {
        ITEMS.add(new MusicItem(R.drawable.album_cover_death_cab, "I will possess your heart", "Death Cab for Cutie", 515));
        ITEMS.add(new MusicItem(R.drawable.album_cover_the_1975, "You", "the 1975", 591));
        ITEMS.add(new MusicItem(R.drawable.album_cover_pinback, "The Yellow Ones", "Pinback", 215));
        ITEMS.add(new MusicItem(R.drawable.album_cover_soad, "Chop suey", "System of a down", 242));
        ITEMS.add(new MusicItem(R.drawable.album_cover_two_door, "Something good can work", "Two Door Cinema Club", 164));
    }

    public static class MusicItem {

        private final int mCover;
        private final String mTitle;
        private final String mArtist;
        private final long mDuration;

        public MusicItem(int cover, String title, String artist, long duration) {
            mCover = cover;
            mTitle = title;
            mArtist = artist;
            mDuration = duration;
        }

        public int getCover() {
            return mCover;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getArtist() {
            return mArtist;
        }

        public long getDuration() {
            return mDuration;
        }
    }
}
