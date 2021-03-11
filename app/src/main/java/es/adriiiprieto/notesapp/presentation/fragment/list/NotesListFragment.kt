package es.adriiiprieto.notesapp.presentation.fragment.list

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import es.adriiiprieto.notesapp.base.BaseExtraData
import es.adriiiprieto.notesapp.base.BaseFragment
import es.adriiiprieto.notesapp.base.util.toTimestampString
import es.adriiiprieto.notesapp.databinding.FragmentNotesListBinding
import es.adriiiprieto.notesapp.domain.model.NoteDomainModel
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.util.*

@AndroidEntryPoint
class NotesListFragment : BaseFragment<NotesListState, NotesListViewModel, FragmentNotesListBinding>() {

    override val viewModelClass: Class<NotesListViewModel> = NotesListViewModel::class.java

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotesListBinding = FragmentNotesListBinding::inflate

    lateinit var mAdapter: NotesListAdapter

    lateinit var vm: NotesListViewModel

    override fun setupView(viewModel: NotesListViewModel) {
        vm = viewModel

        setupRecyclerView()
        setupButton()
        setupWorkManager()
    }

    override fun onNormal(data: NotesListState) {
        binding.fragmentNotesListImageViewNoContent.visibility = if (data.notesList.isNotEmpty()) View.GONE else View.VISIBLE
        binding.fragmentNotesListRecyclerView.visibility = if (data.notesList.isEmpty()) View.GONE else View.VISIBLE
        binding.fragmentNotesListFabDownload.visibility = if (data.notesList.isEmpty()) View.GONE else View.VISIBLE

        if (data.notesList.isNotEmpty()) {
            mAdapter.updateList(data.notesList)
        }
    }

    override fun onLoading(dataLoading: BaseExtraData?) {

    }

    override fun onError(dataError: Throwable) {

    }

    /**
     * Setup Views
     */
    private fun setupButton() {
        binding.fragmentNotesListFab.setOnClickListener {

            // Crashlytics
//            throw Exception()

            vm.onActionClickOnNewNoteButton()

            findNavController().navigate(NotesListFragmentDirections.actionNotesListFragmentToNotesFormFragment())

            // Notifications
//            NotificationUtil(
//                context = requireActivity(),
//                channelId = "Welcome",
//                channelName = "Canal de bienvenida",
//                channelDescription = "Este es un canal de prueba para nuevas notificaciones",
//                notificationId = 123456789,
//                isClickable = true,
//                setTitle = "Holaa!!",
//                setContent = "Ejemplo",
//                setLongContent = "Sample of content",
//                isColorAccent = true,
//                idColorized = true
//            )

        }

        binding.fragmentNotesListFabDownload.setOnClickListener {
            createFile()
        }
    }

    private fun setupRecyclerView() {
        mAdapter = NotesListAdapter(listOf(), object : NotesListAdapter.MyClicksListener {
            override fun onEditButtonClicked(item: NoteDomainModel) {
                vm.onActionClickOnEditNoteButton()
                findNavController().navigate(NotesListFragmentDirections.actionNotesListFragmentToNotesFormFragment(item))
            }

            override fun onDeleteButtonClicked(item: NoteDomainModel) {
                vm.onActionDeleteNote(item)
            }
        })
        binding.fragmentNotesListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setupWorkManager() {
        WorkManager.getInstance(requireActivity().applicationContext).getWorkInfosByTagLiveData("saveInfo").observe(viewLifecycleOwner) { workInfo ->
            workInfo.firstOrNull()?.let { task ->
                if (task.state == WorkInfo.State.SUCCEEDED) {
                    vm.requestInformation()
                }
            }
        }
    }


    /**
     * Save document with all the data
     * 1. Request a name and location for the file
     * 2. Save the content inside the file
     */
    private fun createFile() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/csv"
            putExtra(Intent.EXTRA_TITLE, "My_DB_${Date().time.toTimestampString()}.csv")
        }
        resultLauncher.launch(intent)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val uri = data?.data
            uri?.let {
                writeIntFile(it, vm.getDataCSV())
            }
        }
    }

    private fun writeIntFile(uri: Uri, dataCSV: String) {
        try {
            BufferedWriter(OutputStreamWriter(requireActivity().contentResolver.openOutputStream(uri))).apply {
                write(dataCSV)
                flush()
                close()
            }

            Snackbar.make(binding.fragmentNotesListFabDownload, "Se han guardado los datos correctamente", Snackbar.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Snackbar.make(binding.fragmentNotesListFabDownload, "Hubo un error al intentar guardar los datos", Snackbar.LENGTH_SHORT).setAction("Reintentar") {
                createFile()
            }.show()
        }
    }

}