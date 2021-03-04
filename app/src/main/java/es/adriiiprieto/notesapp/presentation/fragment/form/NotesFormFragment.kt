package es.adriiiprieto.notesapp.presentation.fragment.form

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import es.adriiiprieto.notesapp.R
import es.adriiiprieto.notesapp.base.BaseExtraData
import es.adriiiprieto.notesapp.base.BaseFragment
import es.adriiiprieto.notesapp.databinding.FragmentNotesFormBinding
import es.adriiiprieto.notesapp.presentation.MainActivity

@AndroidEntryPoint
class NotesFormFragment : BaseFragment<NotesFormState, NotesFormViewModel, FragmentNotesFormBinding>() {

    override val viewModelClass: Class<NotesFormViewModel> = NotesFormViewModel::class.java

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotesFormBinding = FragmentNotesFormBinding::inflate

    lateinit var vm: NotesFormViewModel

    private val args: NotesFormFragmentArgs by navArgs()

    companion object {
        const val FIELD_KEY_TITLE = "title"
        const val FIELD_KEY_BODY = "body"
        const val FIELD_KEY_ALL = "all"
        const val CODE_ALL_RIGHT = 1000
        const val CODE_CONFIRM_UPDATE = 1001
        const val CODE_EXIT = 1002
    }

    override fun setupView(viewModel: NotesFormViewModel) {
        vm = viewModel

        setupEditText()
        setupButton()
        setupToolbar(args.inputStateNote == null)

        vm.setNote(args.inputStateNote)
    }

    private fun setupToolbar(create: Boolean) {
        (requireActivity() as MainActivity).setupToolbar(if (create) getString(R.string.notesFormFragmnetToolbarTitleCreate) else getString(R.string.notesFormFragmnetToolbarTitleUpdate))
    }

    override fun onNormal(data: NotesFormState) {
        // Hide progress bar
        binding.fragmentNotesFormProgressBar.visibility = View.GONE

        // Hide errors
        binding.fragmentNotesFormTextInputLayoutTitle.error = null
        binding.fragmentNotesFormTextInputLayoutBody.error = null

        // Set values
        binding.fragmentNotesFormTextInputEditTextTitle.setText(data.title)
        binding.fragmentNotesFormTextInputEditTextBody.setText(data.body)
        binding.fragmentNotesFormButtonSave.text = if (data.note != null) getString(R.string.notesFormFragmentButtonTextUpdate) else getString(R.string.notesFormFragmentButtonTextCreate)
    }

    override fun onLoading(dataLoading: BaseExtraData?) {
        dataLoading?.let {
            when (it.type) {
                CODE_ALL_RIGHT ->
                    MaterialAlertDialogBuilder(requireActivity())
                        .setCancelable(false)
                        .setTitle(getString(R.string.notesFormFragmentDialogTitle))
                        .setMessage(getString(R.string.notesFormFragmentDialogMessage))
                        .setPositiveButton(getString(R.string.notesFormFragmentDialogButtonAccept)) { _, _ ->
                            findNavController().popBackStack()
                        }
                        .show()
                CODE_CONFIRM_UPDATE ->
                    MaterialAlertDialogBuilder(requireActivity())
                        .setCancelable(false)
                        .setTitle(getString(R.string.notesFormFragmentDialogTitle))
                        .setMessage(getString(R.string.notesFormFragmentDialogMessageConfirmation))
                        .setPositiveButton(getString(R.string.notesFormFragmentDialogButtonAccept)) { _, _ ->
                            vm.onActionUpdateNote()
                        }
                        .setNegativeButton(getString(R.string.notesFormFragmentDialogButtonCancelConfirmation)) { _, _ ->
                        }
                        .show()
                CODE_EXIT -> findNavController().popBackStack()
                else -> {
                }
            }
        } ?: run {
            binding.fragmentNotesFormProgressBar.visibility = View.VISIBLE
        }
    }

    override fun onError(dataError: Throwable) {
        if (dataError is FieldErrorException) {
            when (dataError.fieldName) {
                FIELD_KEY_TITLE -> binding.fragmentNotesFormTextInputLayoutTitle.error = getString(R.string.notesFormFragmentErrorEmptyField)
                FIELD_KEY_BODY -> binding.fragmentNotesFormTextInputLayoutBody.error = getString(R.string.notesFormFragmentErrorEmptyField)
                FIELD_KEY_ALL -> {
                    binding.fragmentNotesFormTextInputLayoutTitle.error = getString(R.string.notesFormFragmentErrorEmptyField)
                    binding.fragmentNotesFormTextInputLayoutBody.error = getString(R.string.notesFormFragmentErrorEmptyField)
                }
                else -> {
                }
            }
        }
    }


    /**
     * Setup view
     */
    private fun setupEditText() {
        binding.fragmentNotesFormTextInputEditTextTitle.doOnTextChanged { inputText, _, _, _ ->
            vm.onActionSetTitle(inputText.toString())
        }
        binding.fragmentNotesFormTextInputEditTextBody.doOnTextChanged { inputText, _, _, _ ->
            vm.onActionSetBody(inputText.toString())
        }
    }

    private fun setupButton() {
        binding.fragmentNotesFormButtonSave.setOnClickListener {
            vm.onActionSaveNote()
        }
    }

}