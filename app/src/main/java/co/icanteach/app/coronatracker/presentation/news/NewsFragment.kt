package co.icanteach.app.coronatracker.presentation.news

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import co.icanteach.app.coronatracker.R
import co.icanteach.app.coronatracker.appComponent
import co.icanteach.app.coronatracker.core.observeNonNull
import co.icanteach.app.coronatracker.presentation.news.inject.NewsComponent
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

class NewsFragment : Fragment(R.layout.fragment_news) {

    private lateinit var newsComponent: NewsComponent

    private val viewModel: NewsViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var newsAdapter: NewsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        newsComponent = appComponent.newsComponent.create().also {
            it.inject(this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.adapter = newsAdapter
        newsAdapter.onNewsItemClick = {
            openWebContent(it)
        }
        viewModel.getNewsResult().observeNonNull(viewLifecycleOwner) { news ->
            newsAdapter.setNews(news)
        }
    }


    private fun openWebContent(url: String) {
        val builder = CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
            .addDefaultShareMenuItem()
            .setShowTitle(true)
            .setStartAnimations(
                requireContext(),
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            .setExitAnimations(
                requireContext(),
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(), url.toUri())
    }
}