package com.codemitry.wearer.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.codemitry.wearer.ComponentsProvider
import com.codemitry.wearer.R
import com.codemitry.wearer.databinding.FragmentAccountBinding
import com.codemitry.wearer.models.AuthType
import com.codemitry.wearer.models.ImageResource
import com.codemitry.wearer.mvp.contracts.account.AccountContract
import javax.inject.Inject

class AccountFragment : Fragment(), AccountContract.View {

    private var _binding: FragmentAccountBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!


    @Inject
    lateinit var presenter: AccountContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        (requireActivity().application as ComponentsProvider).accountComponentBuilder.build().inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signOut.setOnClickListener {
            presenter.onSignOutClick()
        }
    }

    override fun onStart() {
        super.onStart()

        presenter.onViewAttached(this)
    }

    override fun onStop() {
        super.onStop()

        presenter.onViewDetached()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showSignInActivity() {
        SignInActivity.start(requireContext())
        requireActivity().finish()
    }

    override fun showUsername(username: String) {
        binding.userName.text = username
    }

    override fun showUid(uid: String) {
        binding.uid.text = uid
    }

    override fun showUserPhoto(image: ImageResource) {
        Glide.with(requireContext())
            .load(image.uri)
            .error(image.localResId)
            .transform(CircleCrop())
            .into(binding.userPhoto)
    }

    override fun showAuthType(authType: AuthType) {
        val authTypeAsResource = when (authType) {
            AuthType.GOOGLE -> R.string.signedWithGoogle
            AuthType.ANONYMOUS -> R.string.signedAnonymously
            AuthType.UNKNOWN -> R.string.signedWayUnknown
        }
        binding.authType.text = getString(authTypeAsResource)
    }
}