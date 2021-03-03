package es.adriiiprieto.notesapp.presentation.fragment.form

import android.view.LayoutInflater
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import es.adriiiprieto.notesapp.base.BaseExtraData
import es.adriiiprieto.notesapp.base.BaseFragment
import es.adriiiprieto.notesapp.databinding.FragmentNotesFormBinding

@AndroidEntryPoint
class NotesFormFragment : BaseFragment<NotesFormState, NotesFormViewModel, FragmentNotesFormBinding>() {

    override val viewModelClass: Class<NotesFormViewModel> = NotesFormViewModel::class.java

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotesFormBinding = FragmentNotesFormBinding::inflate

    override fun setupView(viewModel: NotesFormViewModel) {

    }

    override fun onNormal(data: NotesFormState) {

    }

    override fun onLoading(dataLoading: BaseExtraData?) {

    }

    override fun onError(dataError: Throwable) {

    }

}