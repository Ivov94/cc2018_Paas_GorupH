using Android.Content;
using Android.Graphics;
using Android.OS;
using Android.Support.V4.App;
using Android.Util;
using Android.Views;
using Android.Widget;
using Com.Bumptech.Glide;
using Com.Bumptech.Glide.Request;
using Java.IO;
using Paas.GroupH.Model;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AndroidFile = Java.IO.File;

namespace Paas.GroupH.Fragments
{
    public class Fragment1 : Fragment
    {
        private ImageView imageView;
        private int index;
        public int Index
        {
            get { return index; }
            set { index = value; }
        }

        private Button prevImage;
        private Button nextImage;
        private Button uploadImage;
        
        private string[] Files
        {
            get
            {
                var files = System.IO.Directory.GetFiles(Helper.Values.FolderPath, "*.jpg");
                
                return files;
            }
        }

        public override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your fragment here
            if (Files != null && Files.Length > 0)
                Index = (Files.Length - 1);
        }
        public override void OnDestroyView()
        {
            base.OnDestroyView();
            Glide.With(this).Clear(imageView);
            Glide.With(this).Dispose();
        }

        public static Fragment1 NewInstance()
        {
            var frag1 = new Fragment1 { Arguments = new Bundle() };
            return frag1;
        }


        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            var ignored = base.OnCreateView(inflater, container, savedInstanceState);

            return inflater.Inflate(Resource.Layout.fragment1, null);
        }
        public override void OnViewCreated(View view, Bundle savedInstanceState)
        {
            base.OnViewCreated(view, savedInstanceState);

            imageView = view.FindViewById<ImageView>(Resource.Id.imageView);
            prevImage = view.FindViewById<Button>(Resource.Id.prevImage);
            nextImage = view.FindViewById<Button>(Resource.Id.nextImage);
            uploadImage = view.FindViewById<Button>(Resource.Id.sendImage);


            if(Files != null && Files.Length > 0)
            {

                RequestOptions options = new RequestOptions().EncodeQuality(50).FitCenter();

                Glide.With(this).Load(Files[Index]).Apply(options).Into(imageView);

                nextImage.Click += delegate
                                {
                                    OpenNextImage(options);
                                };
                prevImage.Click += delegate
                                {
                                    OpenPrevImage(options);
                                };

                uploadImage.Click += delegate
                                {
                                    UploadImage();
                                };
            }
            
        }

        private void OpenNextImage(RequestOptions options)
        {
            Index = Index+1;

            if (Index > (int)(Files.Length-1))
                Index = 0;
            
            Glide.With(this).Load(Files[Index]).Apply(options).Into(imageView);

        }

        private void OpenPrevImage(RequestOptions options)
        {
            Index = Index-1;
            if(Index <= -1)
                Index = (Files.Length - 1);

            Glide.With(this).Load(Files[Index]).Apply(options).Into(imageView);
            
        }

        private void UploadImage()
        {
            var uri = Android.Net.Uri.FromFile(new Java.IO.File(Files[Index]));

            var input = Activity.ContentResolver.OpenInputStream(uri);

            try
            {
                int indx = Files[Index].LastIndexOf("/") + 1;

                var image = RestService.PostData(input, Files[Index].Substring(indx));

                Toast.MakeText(this.Context, string.Format("Image uploading... please wait.. when image uploaded, you will be redirected to result page.."), ToastLength.Long).Show();

                image.ContinueWith(test =>
                {
                    //if(image.IsCompletedSuccessfully)
                    {
                        var fr4 = new Intent(this.Activity, typeof(RestActivity));
                        fr4.PutExtra("fileName", Files[Index].Substring(indx));
                        fr4.PutExtra("result", test.Result);
                        StartActivity(fr4);
                    }
                    
                }, TaskContinuationOptions.RunContinuationsAsynchronously);

            }
            catch (System.Exception ex)
            {
                Toast.MakeText(this.Context, string.Format("There was an exception {0}", ex.Message), ToastLength.Short).Show();
            }
        }
    }
}