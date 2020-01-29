package managers;

import androidx.fragment.app.Fragment;

public interface RunningInterface {
    public float getDistance();
    public Fragment getFragment();
    public void update();
    public void pause();
    public void reset();
}
