/*
 * Copyright (c) 2017-present Ilhasoft.
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

package com.airasia.supportwheeloffate.utils.validationLib.rule;

import android.text.TextUtils;
import android.widget.TextView;

import com.airasia.supportwheeloffate.utils.validationLib.util.EditTextHandler;


/**
 * Created by john-mac on 5/14/16.
 */
public class EmptyRule extends Rule<TextView, Boolean> {

    public EmptyRule(TextView view, Boolean value, String errorMessage) {
        super(view, value, errorMessage);
    }

    @Override
    public boolean isValid(TextView view) {
        return !value || !TextUtils.isEmpty(view.getText());
    }

    @Override
    public void onValidationSucceeded(TextView view) {
        EditTextHandler.removeError(view);
    }

    @Override
    public void onValidationFailed(TextView view) {
        EditTextHandler.setError(view, errorMessage);
    }
}
