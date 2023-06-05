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

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

import com.kunminx.architecture.data.response.DataResult;
import com.kunminx.architecture.data.response.ResponseStatus;
import com.kunminx.architecture.data.response.ResultSource;
import com.kunminx.architecture.domain.message.MutableResult;
import com.kunminx.architecture.domain.message.Result;
import com.kunminx.architecture.domain.request.Requester;
import com.kunminx.puremusic.data.bean.User;
import com.kunminx.puremusic.data.repository.DataRepository;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by KunMinX at 20/04/26
 */
public class AccountRequester extends Requester implements DefaultLifecycleObserver {

  private final MutableResult<DataResult<String>> tokenResult = new MutableResult<>();

  public Result<DataResult<String>> getTokenResult() {
    return tokenResult;
  }

  private Disposable mDisposable;

  public void requestLogin(User user) {
    DataRepository.getInstance().login(user)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Observer<DataResult<String>>() {
        @Override
        public void onSubscribe(Disposable d) {
          mDisposable = d;
        }
        @Override
        public void onNext(DataResult<String> dataResult) {
          tokenResult.postValue(dataResult);
        }
        @Override
        public void onError(Throwable e) {
          tokenResult.postValue(new DataResult<>(null,
            new ResponseStatus(e.getMessage(), false, ResultSource.NETWORK)));
        }
        @Override
        public void onComplete() {
          mDisposable = null;
        }
      });
  }

  public void cancelLogin() {
    if (mDisposable != null && !mDisposable.isDisposed()) {
      mDisposable.dispose();
      mDisposable = null;
    }
  }

  @Override
  public void onStop(@NonNull @NotNull LifecycleOwner owner) {
    cancelLogin();
  }
}
