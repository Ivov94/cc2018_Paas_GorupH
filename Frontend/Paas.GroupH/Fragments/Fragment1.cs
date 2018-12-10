using Android.Content;
using Android.OS;
using Android.Support.V4.App;
using Android.Views;
using Android.Widget;
using System.Collections.Generic;


namespace Paas.GroupH.Fragments
{
    public class Fragment1 : Fragment
    {
        private GridView gridView;
        
        public override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your fragment here
        }

        public static Fragment1 NewInstance()
        {
            var frag1 = new Fragment1 { Arguments = new Bundle() };
            return frag1;
        }


        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View view = inflater.Inflate(Resource.Layout.fragment1, null);

            gridView = Activity.FindViewById<GridView>(Resource.Id.gridview);


            //gridView.Adapter = new ImageAdapter(Activity);

            //gridView.ItemClick += delegate (object sender, AdapterView.ItemClickEventArgs args) {
            //    Toast.MakeText(Activity, args.Position.ToString(), ToastLength.Short).Show();
            //};

            return view;
        }

    }


    public class ImageAdapter : BaseAdapter
    {
        Context context;

        public ImageAdapter(Context c)
        {
            context = c;
        }

        public override int Count
        {
            get { return thumbIds.Length; }
        }

        public override Java.Lang.Object GetItem(int position)
        {
            return null;
        }

        public override long GetItemId(int position)
        {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public override View GetView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView;

            if (convertView == null)
            {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(context);
                imageView.LayoutParameters = new GridView.LayoutParams(85, 85);
                imageView.SetScaleType(ImageView.ScaleType.CenterCrop);
                imageView.SetPadding(8, 8, 8, 8);
            }
            else
            {
                imageView = (ImageView)convertView;
            }

            imageView.SetImageResource(thumbIds[position]);
            return imageView;
        }

        // references to our images
        int[] thumbIds = {
            Resource.Drawable.Icon, Resource.Drawable.Icon,
            Resource.Drawable.Icon, Resource.Drawable.Icon,
            Resource.Drawable.Icon, Resource.Drawable.Icon,
            Resource.Drawable.Icon, Resource.Drawable.Icon,
            Resource.Drawable.Icon, Resource.Drawable.Icon,
            Resource.Drawable.Icon, Resource.Drawable.Icon,
            Resource.Drawable.Icon, Resource.Drawable.Icon,
            Resource.Drawable.Icon, Resource.Drawable.Icon,
            Resource.Drawable.Icon, Resource.Drawable.Icon,
        };
    }
}