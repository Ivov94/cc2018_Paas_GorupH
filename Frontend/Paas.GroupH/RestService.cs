using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Android.App;
using Android.Content;
using Android.Graphics;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;
using Org.Apache.Http.Client;
using Org.Apache.Http.Protocol;
using Paas.GroupH.Fragments;
using Paas.GroupH.Model;

namespace Paas.GroupH
{
    public static class RestService
    {
        public static string BaseUrl = Helper.Values.Host + ":" + Helper.Values.Port;

        public async static Task<string> PostData(Stream image, string filename)
        {
            StringBuilder sb = new StringBuilder();
            string name = filename;

            
            
            try {
                using (var content = new MultipartFormDataContent())
                {

                    using (var httpClient = new HttpClient())
                    {
                        httpClient.Timeout = TimeSpan.FromMinutes(2);
                        content.Add(new StreamContent(image), "file", filename);
                        content.Add(new StringContent(filename));
                        content.Add(new StringContent(name));


                        Log.Info(Helper.Values.LogTag, " posting image...");

                        sb.AppendLine(string.Format("posting image... {0}", filename));

                        var httpResponseMessage = await httpClient.PostAsync((BaseUrl + Helper.Values.DefaultUploadPath), content);

                        if (httpResponseMessage.IsSuccessStatusCode)
                        {

                            Log.Info(Helper.Values.LogTag, "image uploaded...");
                            sb.AppendLine(string.Format("image uploaded..."));
                            Log.Info(Helper.Values.LogTag, "waiting for response from server...");

                            var result = await httpResponseMessage.Content.ReadAsStringAsync();
                            Log.Info(Helper.Values.LogTag, string.Format("result.. {0}", result));

                        }
                        else
                        {
                            Log.Info(Helper.Values.LogTag, string.Format("ERROR.. not success..."));
                            sb.AppendLine(string.Format("error on uploading..."));
                        }

                        return sb.ToString();
                    }
                    
                }
                
            }
            catch(Exception ex)
            {
                Log.Info(Helper.Values.LogTag, string.Format("ERROR.. {0}", ex.Message));
                return ex.Message;
            }
        }
        public static async Task<RestProgressModel> GetProgressingData(string path, string filename)
        {
            string url = BaseUrl + path + filename;

            using (var httpClient = new HttpClient())
            {
                httpClient.Timeout = TimeSpan.FromMinutes(2);
                try
                {
                    var httpResponse = await httpClient.GetAsync(url);
                    var result = await httpResponse.Content.ReadAsStringAsync();
                    var item = Newtonsoft.Json.JsonConvert.DeserializeObject<RestProgressModel>(result);

                    //Log.Info(Helper.Values.LogTag, string.Format("try {0}: filename {1} red {2} blue {3} green {4} negative {5} image-join {6}", " 1 ", item.ImageName, item.FilterRedDone, item.FilterBlueDone, item.FilterGreenDone, item.FilterNegativeDone, item.ImageJoinDone));

                    return item;
                }
                catch (Exception ex)
                {

                    throw new Exception(ex.Message);
                }
            }
        }

        public static async Task<List<RestProgressModel>> GetProgressingDataList(string path, string filename)
        {
            int maxTryCount = 100;
            var list = new List<RestProgressModel>();

            bool filterNegativeDone = false;
            bool filterRedDone = false;
            bool filterGreenDone = false;
            bool filterBlueDone = false;
            bool imageJoinDone = false;
            int numberOfAttempts = 0;
            string url = BaseUrl + path + filename;
            
            using (var httpClient = new HttpClient())
            {
                httpClient.Timeout = TimeSpan.FromMinutes(2);
                do
                {
                    try
                    {
                        var httpResponse = await httpClient.GetAsync(url);
                        var result = await httpResponse.Content.ReadAsStringAsync();
                        var item = Newtonsoft.Json.JsonConvert.DeserializeObject<RestProgressModel>(result);

                        list.Add(item);

                        filterBlueDone = item.FilterBlueDone;
                        filterGreenDone = item.FilterGreenDone;
                        filterNegativeDone = item.FilterNegativeDone;
                        filterRedDone = item.FilterRedDone;
                        imageJoinDone = item.ImageJoinDone;

                        Log.Info(Helper.Values.LogTag, string.Format("try {0}: filename {1} red {2} blue {3} green {4} negative {5} image-join {6}", numberOfAttempts, item.ImageName, item.FilterRedDone, item.FilterBlueDone, item.FilterGreenDone, item.FilterNegativeDone, item.ImageJoinDone));

                        if (filterBlueDone && filterGreenDone && filterNegativeDone && filterRedDone && imageJoinDone)
                            break;
                    }
                    catch (Exception ex)
                    {
                        if (numberOfAttempts > maxTryCount)
                            throw new Exception("Max 100 try");

                        throw new Exception(ex.Message);
                    }
                    numberOfAttempts++;
                } while (numberOfAttempts < maxTryCount);
            }
                

            return list;
        }

        public static async Task<RestImageModel> GetImageData(string path, string filename)
        {
            string url = BaseUrl + path + filename;
            using (var httpClient = new HttpClient())
            {
                httpClient.Timeout = TimeSpan.FromMinutes(2);
                var httpResponse = await httpClient.GetAsync(url);
                if (httpResponse.IsSuccessStatusCode)
                {
                    var result = await httpResponse.Content.ReadAsStringAsync();

                    var deserializedResult = Newtonsoft.Json.JsonConvert.DeserializeObject<RestImageModel>(result);

                    return deserializedResult;
                }
            }
            
            return null;
        }
    }

    
}