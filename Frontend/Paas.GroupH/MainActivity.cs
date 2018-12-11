using System;
using Android.App;
using Android.Content;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;
using Android.Support.V4.App;
using Android.Support.V4.View;
using Android.Support.Design.Widget;
using Android.Support.V7.App;
using Paas.GroupH.Fragments;
using Android;
using Android.Provider;
using Android.Graphics;

using AndroidFile = Java.IO.File;
using Android.Support.V4.Content;
using Paas.GroupH.Model;
using System.IO;

namespace Paas.GroupH
{
    [Activity(Label = "@string/app_name", MainLauncher = true, LaunchMode = Android.Content.PM.LaunchMode.SingleTop, Icon = "@drawable/icon")]
    public class MainActivity : AppCompatActivity
    {
        ViewPager pager;
        TabsAdapter adapter;

        private static string[] PERMISSIONS_NEEDED =
        {
            Manifest.Permission.Camera,
            Manifest.Permission.ReadExternalStorage,
            Manifest.Permission.WriteExternalStorage
        };

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            SetContentView(Resource.Layout.main);
            var permissionManager = new PermissionsManager(this, PERMISSIONS_NEEDED);
            permissionManager.EnablePermissions();

            CreateDir();


            var toolbar = FindViewById<Android.Support.V7.Widget.Toolbar>(Resource.Id.toolbar);
            if (toolbar != null)
            {
                SetSupportActionBar(toolbar);
                SupportActionBar.SetDisplayHomeAsUpEnabled(false);
                SupportActionBar.SetHomeButtonEnabled(false);

            }

            adapter = new TabsAdapter(this, SupportFragmentManager);
            pager = FindViewById<ViewPager>(Resource.Id.pager);
            var tabs = FindViewById<TabLayout>(Resource.Id.tabs);
            pager.Adapter = adapter;
            tabs.SetupWithViewPager(pager);
            pager.OffscreenPageLimit = 3;
            
        }

        private void CreateDir()
        {
            var directory = new AndroidFile(Android.OS.Environment.ExternalStorageDirectory, "grouph");

            Helper.Values.FolderPath = directory.Path;

            if (!directory.Exists())
                directory.Mkdirs();

            if(!System.IO.File.Exists(System.IO.Path.Combine(directory.Path, "config.json")))
            {
                var settings = new ConfigModel()
                {
                    BaseUrl = Helper.Values.DefaultHost,
                    Port = Helper.Values.DefaultPort,
                    Path = Helper.Values.FolderPath
                };

                var save = Newtonsoft.Json.JsonConvert.SerializeObject(settings);

                try
                {
                    using (StreamWriter writer = new StreamWriter(System.IO.Path.Combine(directory.Path, "config.json")))
                    {
                        writer.Write(save);
                    }
                }
                catch(Exception ex)
                {
                }
                
                    
                    
            }

        }

        class TabsAdapter : FragmentStatePagerAdapter
        {
            string[] titles;

            public override int Count
            {
                get
                {
                    return titles.Length;
                }
            }

            public TabsAdapter(Android.Content.Context context, Android.Support.V4.App.FragmentManager fm) : base(fm)
            {
                titles = context.Resources.GetTextArray(Resource.Array.sections);
            }

            public override Java.Lang.ICharSequence GetPageTitleFormatted(int position)
            {
                return new Java.Lang.String(titles[position]);
            }

            public override Android.Support.V4.App.Fragment GetItem(int position)
            {
                switch (position)
                {
                    case 0:
                        return Fragment1.NewInstance();
                    case 1:
                        return Fragment2.NewInstance();
                    case 2:
                        return Fragment3.NewInstance();
                }
                return null;
            }

            public override int GetItemPosition(Java.Lang.Object frag)
            {
                return PositionNone;
            }
        }
    }
}

