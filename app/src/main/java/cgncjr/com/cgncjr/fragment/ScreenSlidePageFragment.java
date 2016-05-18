package cgncjr.com.cgncjr.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cgncjr.com.cgncjr.R;
import cgncjr.com.cgncjr.utils.ImageOperUtil;


/**
 * 引导图显示
 */
public class ScreenSlidePageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for
     * {@link #ARG_PAGE}.
     */
    private int mPageNumber;
//    static int[] images = new int[]{
//            R.drawable.icon_guid_first,
//            R.drawable.icon_guid_second,
//            R.drawable.icon_guid_third,
//           // R.drawable.icon_guid_fourth
//    };
    static int[] images;

    static View.OnClickListener listener;
    /**
     * Factory method for this fragment class. Constructs a new fragment for the
     * given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber,View.OnClickListener listener,int[] images) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        ScreenSlidePageFragment.listener = listener;
        ScreenSlidePageFragment.images=images;
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        ((ImageView) rootView.findViewById(R.id.image)).setBackgroundDrawable(ImageOperUtil.readResImgAndOptions(getActivity(), images[mPageNumber]));
        if (mPageNumber == images.length -1){
            rootView.findViewById(R.id.image).setOnClickListener(listener);
        }

        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }

}