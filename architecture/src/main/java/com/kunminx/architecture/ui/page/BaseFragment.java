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

package com.kunminx.architecture.ui.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.kunminx.architecture.ui.scope.ViewModelScope;

/**
 * Create by KunMinX at 19/7/11
 */
public abstract class BaseFragment extends DataBindingFragment {

  private final ViewModelScope mViewModelScope = new ViewModelScope();

  protected <T extends ViewModel> T getFragmentScopeViewModel(@NonNull Class<T> modelClass) {
    return mViewModelScope.getFragmentScopeViewModel(this, modelClass);
  }

  protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
    return mViewModelScope.getActivityScopeViewModel(mActivity, modelClass);
  }

  protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
    return mViewModelScope.getApplicationScopeViewModel(modelClass);
  }

  protected NavController nav() {
    return NavHostFragment.findNavController(this);
  }

  protected void toggleSoftInput() {
    InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
  }

  protected void openUrlInBrowser(String url) {
    Uri uri = Uri.parse(url);
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    startActivity(intent);
  }

  protected Context getApplicationContext() {
    return mActivity.getApplicationContext();
  }

}
