/*
 * Copyright 2021 Appmattus Limited
 * Copyright 2019 Babylon Partners Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * File modified by Appmattus Limited
 * See: https://github.com/appmattus/certificatetransparency/compare/e3d469df9be35bcbf0f564d32ca74af4e5ca4ae5...main
 */

package com.babylon.certificatetransparency.sampleapp.item

import android.view.View
import com.babylon.certificatetransparency.sampleapp.R
import com.babylon.certificatetransparency.sampleapp.databinding.CheckboxItemBinding
import com.xwray.groupie.viewbinding.BindableItem

typealias CheckboxCallback = ((Boolean) -> Unit)

class CheckboxItem(
    private val title: String,
    private val isChecked: Boolean,
    private val callback: CheckboxCallback? = null
) : BindableItem<CheckboxItemBinding>() {

    override fun initializeViewBinding(view: View) = CheckboxItemBinding.bind(view)

    override fun getLayout() = R.layout.checkbox_item

    override fun bind(viewBinding: CheckboxItemBinding, position: Int) {
        viewBinding.checkbox.text = title
        viewBinding.checkbox.isChecked = isChecked

        viewBinding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            callback?.invoke(isChecked)
        }
    }

    override fun isSameAs(other: com.xwray.groupie.Item<*>): Boolean {
        return (other is CheckboxItem && title == other.title && isChecked == other.isChecked)
    }
}
