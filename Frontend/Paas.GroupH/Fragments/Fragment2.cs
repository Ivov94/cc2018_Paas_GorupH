using Android;
using Android.Content;
using Android.Graphics;

using Android.OS;
using Android.Provider;
using Android.Support.V4.App;
using Android.Support.V4.Content;
using Android.Views;
using Android.Widget;
using Com.Bumptech.Glide;
using Com.Bumptech.Glide.Request;
using System.Reflection;
using System.Threading.Tasks;
using AndroidFile = Java.IO.File;


namespace Paas.GroupH.Fragments
{
    public class Fragment2 : Fragment
    {
        private ImageView imgView;
        private AndroidFile file;
        private AndroidFile directory;

        private Button btnCamera;
        private Button btnPostImage;
        private TextView infoPost;

        public override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            
        }

        public static Fragment2 NewInstance()
        {
            var frag2 = new Fragment2 { Arguments = new Bundle() };
            return frag2;
        }

        private string UniqueImgFileName() => $"groupH_{System.DateTime.Now.Ticks.ToString()}.jpg";
        private string _providerName = $"grouph";

        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            var ignored = base.OnCreateView(inflater, container, savedInstanceState);

            return inflater.Inflate(Resource.Layout.fragment2, null);
        }

        public override void OnViewCreated(View view, Bundle savedInstanceState)
        {
            base.OnViewCreated(view, savedInstanceState);
            btnCamera = view.FindViewById<Button>(Resource.Id.btntakePicture);
            btnPostImage = view.FindViewById<Button>(Resource.Id.btnPostPicture);
            infoPost = view.FindViewById<TextView>(Resource.Id.infoPost);

            btnCamera.Click += TakeAPicture_Click;

            imgView = view.FindViewById<ImageView>(Resource.Id.imgView);

            btnPostImage.Click += PostRestService_Click;

        }

        private void TakeAPicture_Click(object sender, System.EventArgs e)
        {
            Intent intent = new Intent(MediaStore.ActionImageCapture);
            directory = new AndroidFile(Environment.ExternalStorageDirectory, "grouph");
            if (!directory.Exists())
            {
                directory.Mkdirs();
            }

            file = new AndroidFile(directory, UniqueImgFileName());

            intent.PutExtra(MediaStore.ExtraOutput, FileProvider.GetUriForFile(this.Context, _providerName, file));
            intent.SetFlags(ActivityFlags.GrantWriteUriPermission);
            StartActivityForResult(intent, 0);
        }

        private void PostRestService_Click(object sender, System.EventArgs e)
        {
            var uri = Android.Net.Uri.FromFile(file);

            var input = Activity.ContentResolver.OpenInputStream(uri);

            infoPost.Text = "uploading...";
            infoPost.Visibility = ViewStates.Visible;

            try
            {
                //task started...
                var image = RestService.PostData(input, file.Name);
            }
            catch(System.Exception ex)
            {
                infoPost.Text = string.Format("There was an exception {0}", ex.Message);
            }
            
            


        }

        public override void OnActivityResult(int requestCode, int resultCode, Intent data)
        {
            base.OnActivityResult(requestCode, (int)resultCode, data);
            
            int height = Resources.DisplayMetrics.HeightPixels;
            int width = imgView.Width;

            RequestOptions options = new RequestOptions().EncodeQuality(50).FitCenter();

            Glide.With(this).Load(file.AbsolutePath).Apply(options).Into(imgView);

            btnCamera.Visibility = ViewStates.Invisible;
            btnPostImage.Visibility = ViewStates.Visible;

        }
        public override void OnDestroyView()
        {
            base.OnDestroyView();
            Glide.With(this).Clear(imgView);
            Glide.With(this).Dispose();
        }

    }
}