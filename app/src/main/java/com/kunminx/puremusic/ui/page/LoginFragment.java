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

package com.kunminx.puremusic.ui.page;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kunminx.architecture.data.config.utils.KeyValueProvider;
import com.kunminx.architecture.ui.page.BaseFragment;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.page.StateHolder;
import com.kunminx.architecture.ui.state.State;
import com.kunminx.architecture.utils.ToastUtils;
import com.kunminx.puremusic.BR;
import com.kunminx.puremusic.R;
import com.kunminx.puremusic.data.bean.User;
import com.kunminx.puremusic.data.config.Configs;
import com.kunminx.puremusic.domain.message.DrawerCoordinateManager;
import com.kunminx.puremusic.domain.request.AccountRequester;

/**
 * Create by KunMinX at 20/04/26
 */
public class LoginFragment extends BaseFragment {
  private LoginStates mStates;
  private AccountRequester mAccountRequester;
  private final Configs mConfigs = KeyValueProvider.get(Configs.class);

  @Override
  protected void initViewModel() {
    mStates = getFragmentScopeViewModel(LoginStates.class);
    mAccountRequester = getFragmentScopeViewModel(AccountRequester.class);
  }

  @Override
  protected DataBindingConfig getDataBindingConfig() {
    return new DataBindingConfig(R.layout.fragment_login, BR.vm, mStates)
      .addBindingParam(BR.click, new ClickProxy());
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getLifecycle().addObserver(DrawerCoordinateManager.getInstance());
    getLifecycle().addObserver(mAccountRequester);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mAccountRequester.getTokenResult().observe(getViewLifecycleOwner(), dataResult -> {
      if (!dataResult.getResponseStatus().isSuccess()) {
        mStates.loadingVisible.set(false);
        ToastUtils.showLongToast(getString(R.string.network_state_retry));
        return;
      }

      String s = dataResult.getResult();
      if (TextUtils.isEmpty(s)) return;

      mConfigs.token().set(s);
      mStates.loadingVisible.set(false);

      //TODO 登录成功后进行的下一步操作...
      nav().navigateUp();
    });
  }

  public class ClickProxy {
    public void back() {
      nav().navigateUp();
    }
    public void login() {
      if (TextUtils.isEmpty(mStates.name.get()) || TextUtils.isEmpty(mStates.password.get())) {
        ToastUtils.showLongToast(getString(R.string.username_or_pwd_incomplete));
        return;
      }
      User user = new User(mStates.name.get(), mStates.password.get());
      mAccountRequester.requestLogin(user);
      mStates.loadingVisible.set(true);
    }
  }

  public static class LoginStates extends StateHolder {
    public final State<String> name = new State<>("");
    public final State<String> password = new State<>("");
    public final State<Boolean> loadingVisible = new State<>(false);
  }
}
