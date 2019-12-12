package managers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.core.entities.NamirniceObroka;

public interface NamirnicaImporter {
    public String getName();
    public Fragment getFragment();
    public void setBundle();
}
