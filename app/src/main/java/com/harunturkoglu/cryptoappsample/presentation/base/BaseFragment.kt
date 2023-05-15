package com.harunturkoglu.cryptoappsample.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.harunturkoglu.cryptoappsample.ext.observe
import com.harunturkoglu.cryptoappsample.presentation.ui.common.dialog.GeneralDialog
import com.harunturkoglu.cryptoappsample.presentation.ui.common.dialog.ProgressDialog
import com.harunturkoglu.cryptoappsample.utils.NavAnimOptions
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    protected abstract fun getLayoutRes(): Int
    protected lateinit var binding: DB
    protected abstract fun getViewModelClass(): Class<VM>

    protected val viewModel: VM by lazy { ViewModelProvider(this).get(getViewModelClass()) }

    private lateinit var progressDialog: ProgressDialog

    private val generalDialog: GeneralDialog by lazy {
        GeneralDialog(requireContext())
    }

    /**
     * In need that function can be override by Fragment which inherits that [BaseFragment] class.
     * You can declare your operations in this function which will run on start of page.
     * Use it for ease of reading
     */
    open fun initUi() {}

    /**
     * In need that function can be override by Fragment which inherits that [BaseFragment] class.
     * You can declare your Ui-Related initialize operation in this function.
     * Use it for ease of reading
     */
    open fun initClickListeners() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValue()
        initUi()
        initObservers()
        initClickListeners()
    }

    /**
     * In need that function can be override by Fragment which inherits that [BaseFragment] class.
     * You can declare your observing operation in this function. Use it for ease of reading
     */
    open fun initObservers() {
        lifecycleScope.launch {
            viewModel.errorState.collectLatest {
                showGeneralDialog(descriptionText = it, isError = true)
            }
        }
        observe(viewModel.loadingState) {
            if (it)
                startProgress()
            else
                dismissProgress()
        }
    }


    private fun initValue() {
        progressDialog = ProgressDialog(requireActivity())
    }

    private fun startProgress() {
        if (progressDialog.dialog?.isShowing != true)
            progressDialog.startDialog()
    }

    private fun dismissProgress() {
        progressDialog.dismissDialog()
    }

    fun showToastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun showGeneralDialog(
        descriptionText: String,
        isError: Boolean = false,
        titleText: String? = null,
        positiveButtonText: String? = null,
        negativeButtonText: String? = null,
        positiveButtonCallback: (() -> Unit)? = null,
        negativeButtonCallback: (() -> Unit)? = null,
    ) {
        generalDialog.prepareAndShow(
            descriptionText = descriptionText,
            isError = isError,
            titleText = titleText,
            positiveButtonText = positiveButtonText,
            negativeButtonText = negativeButtonText,
            positiveButtonCallback = positiveButtonCallback,
            negativeButtonCallback = negativeButtonCallback
        )
    }

    fun nav(
        direction: NavDirections,
        navController: NavController? = findNavController(),
        withAnimation: Boolean = true
    ) {
        val currentDirectionsNavOptions = navController?.currentDestination?.getAction(direction.actionId)?.navOptions
        kotlin.runCatching {
            navController?.navigate(directions = direction, navOptions = NavAnimOptions.getNavOptionsSlide(navOptions = currentDirectionsNavOptions))
        }.onFailure {
            Timber.e(it.localizedMessage)
        }
    }
}