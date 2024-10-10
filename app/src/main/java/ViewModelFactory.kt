import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactmanger.ContactViewModel
import com.example.contactmanger.repository.ContactRepository

// if your viewmodel has a constructor with parameters
// you can't use the default constructor that the
// viewModel framework provides.

// ViewModelfactory: pass the required parameters to ViewModel

class ViewModelFactory(private val repository: ContactRepository) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(ContactViewModel::class.java)){

            return ContactViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}