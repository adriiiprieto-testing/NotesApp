package es.adriiiprieto.notesapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<VS : BaseViewState, VM : BaseViewModel<VS>, B : ViewDataBinding> : Fragment() {

    /**
     * ViewModel
     */
    private lateinit var viewModel: VM

    abstract val viewModelClass: Class<VM>

    /**
     * Data binding
     */
    protected lateinit var binding: B

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B

    /**
     * onCreate
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // TODO: Delete invoke and check
        binding = bindingInflater.invoke(inflater, container, false)

        return binding.root
    }

    /**
     * onResume
     */
    override fun onResume() {
        super.onResume()

        // Get or create the viewModel
        viewModel = ViewModelProvider(this).get(viewModelClass)

        // Setup observers
        viewModel.getObservableState().observe(viewLifecycleOwner, { state ->
            onNormal(state.data)
            when (state) {
                is BaseState.Loading -> onLoading(state.dataLoading)
                is BaseState.Error -> onError(state.dataError)
                else -> {
                }
            }
        })

        // Setup view sending the View Model
        setupView(viewModel)

        // Fragment start
        viewModel.onStart()
    }


    /**
     *  Setup view when the viewModel exists
     */
    abstract fun setupView(viewModel: VM)

    /**
     * Manage state functions
     */
    abstract fun onNormal(data: VS)
    abstract fun onLoading(dataLoading: BaseExtraData?)
    abstract fun onError(dataError: Throwable)

}