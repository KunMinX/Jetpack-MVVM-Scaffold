/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kunminx.puremusic.domain.request;

import androidx.lifecycle.ViewModel;

import com.kunminx.architecture.data.response.DataResult;
import com.kunminx.architecture.domain.message.MutableResult;
import com.kunminx.architecture.domain.message.Result;
import com.kunminx.puremusic.data.bean.TestAlbum;
import com.kunminx.puremusic.data.repository.DataRepository;

/**
 * Create by KunMinX at 19/10/29
 */
public class MusicRequester extends ViewModel {

  private final MutableResult<DataResult<TestAlbum>> mFreeMusicsResult = new MutableResult<>();

  public Result<DataResult<TestAlbum>> getFreeMusicsResult() {
    return mFreeMusicsResult;
  }

  public void requestFreeMusics() {
    DataRepository.getInstance().getFreeMusic(mFreeMusicsResult::setValue);
  }
}
