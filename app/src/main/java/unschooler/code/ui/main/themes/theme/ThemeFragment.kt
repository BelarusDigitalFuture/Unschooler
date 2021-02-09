package unschooler.code.ui.main.themes.theme

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.f_schedule.*
import kotlinx.android.synthetic.main.f_schedule.title
import kotlinx.android.synthetic.main.f_theme.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

import unschooler.code.R
import unschooler.code.ui.main.themes.common.SubjectsAdapter
import unschooler.code.utils.alertOk
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ThemeFragment : DaggerFragment(R.layout.f_theme) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by activityViewModels<ThemeViewModel> { viewModelFactory }

    val args: ThemeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSubject(args.theme)?.let{
            title.text = it.title
            it.themes.find { it.id == args.theme }?.let{ theme ->
                themeTitle.text = theme.title
                themeDescription.text = theme.description
            }
        }

        back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}
