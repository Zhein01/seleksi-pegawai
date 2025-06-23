import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tes.admin.LogoutFragment
import com.example.tes.admin.Lowongan.Admin_DaftarLowonganFragment
import com.example.tes.admin.User.ProfileFragment
import com.example.tes.admin.soal.Admin_SoalFragment

class Admin_adapterViewpager(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragmentlist = listOf(
        Admin_DaftarLowonganFragment(),
        Admin_SoalFragment(),
        LogoutFragment()
    )

    override fun getItemCount(): Int = fragmentlist.size

    override fun createFragment(position: Int): Fragment = fragmentlist[position]

}