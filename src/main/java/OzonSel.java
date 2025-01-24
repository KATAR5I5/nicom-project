import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OzonSel {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "F:\\Distr Java\\chromedriver-win64\\chromedriver.exe");

        // Настройка браузера в режиме headless (опционально)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // Запуск в режиме без GUI (если необходимо)
        options.addArguments("--disable-gpu");  // Отключение GPU
        options.addArguments("--no-sandbox");  // Отключение песочницы
        options.addArguments("--disable-blink-features=AutomationControlled");  // Маскировка от Selenium
        options.addArguments("--start-maximized");  // Уберите эту строку, если нужен графический интерфейс
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");


        // Инициализация WebDriver
        WebDriver driver = new ChromeDriver(options);

//        String cook = "ADDRESSBOOKBAR_WEB_CLARIFICATION=1737444542; TS0121feed=0187c00a18e734dcdbb433d3fe5e9416c226962e939dd1f3cd387bae9fa0648d0f597bce42c094af84f9d80233388017d213210adc; __Secure-ETC=a7f3da441dc0255f38baa7604b666a29; __Secure-ab-group=94; __Secure-access-token=7.58130011.hBnr2ghHS0idlA2HRcxGNA.94.AR1aHo6hx4eJj95hmBY-i0r_TyGdwpZk8nzuq1vGE2RZQu5Vxqly6sn_mj2x4Ha8FAMEwrjQ8QZ75ew6SovpER4.20210719080015.20250123205836.MaLOXOwpp02qiTjmweyOO-wKgEnRU-EdGtqiRez54Fw.1eb69a92fc628d21f; __Secure-ext_xcid=2de4deb43ba86ea2567b884cd698c14f; __Secure-refresh-token=7.58130011.hBnr2ghHS0idlA2HRcxGNA.94.AR1aHo6hx4eJj95hmBY-i0r_TyGdwpZk8nzuq1vGE2RZQu5Vxqly6sn_mj2x4Ha8FAMEwrjQ8QZ75ew6SovpER4.20210719080015.20250123205836.utde7rXSyx7vEwoy4yt6zTRNCMRNGoqU9orfyLixBTA.18755f1002d92e87e; __Secure-user-id=0; abt_data=7.ri_HoQ8mg3Qmk2TGmp0lsf96ONvHzXy97_FcWQaaj1jCB43liovKS22DnUBqF4fXneqohKE0lbuEIWJ8CPy8zPeKilexFTRjRrDEMkBQufF9WInlQN6ksdFzvvlXRj1v7IBDcLbHsgiuigYzf0YFGHBIjnRtT9uYgjV69VGC50ltv6R-b_niGoEgeuGpRztySQ-XgNZbreO25-3BLcWrbkAIf7zJhE1WKoCtLH35Hw-3n8_vBvGL9DtQSCGGXXi-Hb6n7NX2MpVJY0KApz5VUTJd4J6YGgwMTkK8x2e5Uv-7Ia-2IQ; ob_theme=DEFAULT; cf_clearance=O_vIHKyJNvlCbE8fjCcb38rJFo39pSMSZ74QzBVKL5Q-1717425520-1.0.1.1-vCfWiM8Vhk_jcoPZYHg83EaXxYXLkhBJFlzZGafM9r0KxmVqICP5dzSnIeD57dhVjVgHQu475RSthsChbsATGQ; xcid=f85f8acc74dc15518814d6b89bcaae62; __Secure-ETC=c803d25ed8fc84c2f2940cbc4aefe7d3; rfuid=NjkyNDcyNDUyLDEyNC4wNDM0NzUyNzUxNjA3NCwxMDI4MjM3MjIzLC0xLC0xOTAwMDQ5ODE1LFczc2libUZ0WlNJNklsQkVSaUJXYVdWM1pYSWlMQ0prWlhOamNtbHdkR2x2YmlJNklsQnZjblJoWW14bElFUnZZM1Z0Wlc1MElFWnZjbTFoZENJc0ltMXBiV1ZVZVhCbGN5STZXM3NpZEhsd1pTSTZJbUZ3Y0d4cFkyRjBhVzl1TDNCa1ppSXNJbk4xWm1acGVHVnpJam9pY0dSbUluMHNleUowZVhCbElqb2lkR1Y0ZEM5d1pHWWlMQ0p6ZFdabWFYaGxjeUk2SW5Ca1ppSjlYWDBzZXlKdVlXMWxJam9pUTJoeWIyMWxJRkJFUmlCV2FXVjNaWElpTENKa1pYTmpjbWx3ZEdsdmJpSTZJbEJ2Y25SaFlteGxJRVJ2WTNWdFpXNTBJRVp2Y20xaGRDSXNJbTFwYldWVWVYQmxjeUk2VzNzaWRIbHdaU0k2SW1Gd2NHeHBZMkYwYVc5dUwzQmtaaUlzSW5OMVptWnBlR1Z6SWpvaWNHUm1JbjBzZXlKMGVYQmxJam9pZEdWNGRDOXdaR1lpTENKemRXWm1hWGhsY3lJNkluQmtaaUo5WFgwc2V5SnVZVzFsSWpvaVEyaHliMjFwZFcwZ1VFUkdJRlpwWlhkbGNpSXNJbVJsYzJOeWFYQjBhVzl1SWpvaVVHOXlkR0ZpYkdVZ1JHOWpkVzFsYm5RZ1JtOXliV0YwSWl3aWJXbHRaVlI1Y0dWeklqcGJleUowZVhCbElqb2lZWEJ3YkdsallYUnBiMjR2Y0dSbUlpd2ljM1ZtWm1sNFpYTWlPaUp3WkdZaWZTeDdJblI1Y0dVaU9pSjBaWGgwTDNCa1ppSXNJbk4xWm1acGVHVnpJam9pY0dSbUluMWRmU3g3SW01aGJXVWlPaUpOYVdOeWIzTnZablFnUldSblpTQlFSRVlnVm1sbGQyVnlJaXdpWkdWelkzSnBjSFJwYjI0aU9pSlFiM0owWVdKc1pTQkViMk4xYldWdWRDQkdiM0p0WVhRaUxDSnRhVzFsVkhsd1pYTWlPbHQ3SW5SNWNHVWlPaUpoY0hCc2FXTmhkR2x2Ymk5d1pHWWlMQ0p6ZFdabWFYaGxjeUk2SW5Ca1ppSjlMSHNpZEhsd1pTSTZJblJsZUhRdmNHUm1JaXdpYzNWbVptbDRaWE1pT2lKd1pHWWlmVjE5TEhzaWJtRnRaU0k2SWxkbFlrdHBkQ0JpZFdsc2RDMXBiaUJRUkVZaUxDSmtaWE5qY21sd2RHbHZiaUk2SWxCdmNuUmhZbXhsSUVSdlkzVnRaVzUwSUVadmNtMWhkQ0lzSW0xcGJXVlVlWEJsY3lJNlczc2lkSGx3WlNJNkltRndjR3hwWTJGMGFXOXVMM0JrWmlJc0luTjFabVpwZUdWeklqb2ljR1JtSW4wc2V5SjBlWEJsSWpvaWRHVjRkQzl3WkdZaUxDSnpkV1ptYVhobGN5STZJbkJrWmlKOVhYMWQsV3lKeWRTMVNWU0pkLDAsMSwwLDI0LDIzNzQxNTkzMCw4LDIyNzEyNjUyMCwwLDEsMjAsLTQ5MTI3NTUyMyxSMjl2WjJ4bElFbHVZeTRnVG1WMGMyTmhjR1VnUjJWamEyOGdWMmx1TXpJZ05TNHdJQ2hYYVc1a2IzZHpJRTVVSURFd0xqQTdJRmRwYmpZME95QjROalFwSUVGd2NHeGxWMlZpUzJsMEx6VXpOeTR6TmlBb1MwaFVUVXdzSUd4cGEyVWdSMlZqYTI4cElFTm9jbTl0WlM4eE16RXVNQzR3TGpBZ1UyRm1ZWEpwTHpVek55NHpOaUF5TURBek1ERXdOeUJOYjNwcGJHeGgsZXlKamFISnZiV1VpT25zaVlYQndJanA3SW1selNXNXpkR0ZzYkdWa0lqcG1ZV3h6WlN3aVNXNXpkR0ZzYkZOMFlYUmxJanA3SWtSSlUwRkNURVZFSWpvaVpHbHpZV0pzWldRaUxDSkpUbE5VUVV4TVJVUWlPaUpwYm5OMFlXeHNaV1FpTENKT1QxUmZTVTVUVkVGTVRFVkVJam9pYm05MFgybHVjM1JoYkd4bFpDSjlMQ0pTZFc1dWFXNW5VM1JoZEdVaU9uc2lRMEZPVGs5VVgxSlZUaUk2SW1OaGJtNXZkRjl5ZFc0aUxDSlNSVUZFV1Y5VVQxOVNWVTRpT2lKeVpXRmtlVjkwYjE5eWRXNGlMQ0pTVlU1T1NVNUhJam9pY25WdWJtbHVaeUo5ZlgxOSw2NSwtMTI4NTU1MTMsMSwxLC0xLDE2OTk5NTQ4ODcsMTY5OTk1NDg4NywzMzYwMzkxNzUsOA==; TS0121feed=0187c00a18e734dcdbb433d3fe5e9416c226962e939dd1f3cd387bae9fa0648d0f597bce42c094af84f9d80233388017d213210adc; TS0149423d=0187c00a1880d773879a3dccc96ca65aadb891ff93bea53313284dcd73a30d6d08bb0a111c54d4ad890f4822240e4a4653a6397921; abt_data=7.c67hAxgQBcSDcqLBKxVkEjgQ-D1OlMJ0iv8rimANLLffUHGr4Sat1fMqDiaQBCwRX7HSinHNJMGVgPTkNV6XZ5LrZoHQf3-Q0wOCtGveBSPm09FIk6CLuU8olbsbaHeIuGhKSJgZ9lIocFotYfxunPfvzfYYeX4q82-b4K-EvfvdVBsydB9yNO1Dep2xIqZmu7e5PHNhePiB8Qw4OjDCxNYmpmYtR4Veau5IQpqWkb0U0V1PR1zIRWCRPM5obGLU568vrzPDCNoFMnn2eVArJsCzSppJMSShp8aHqlAlMsky_bsHTBW7B8q8O-99UONPraAeKIKTREE8V8OmGNvS6WdyCfRybvJHux5tqvOZW28RQbyHhFo7r05n9NjE0ztn_QrMA1RHEuoYpjOSb9uGeMKSORCxqnoYvw3MbRkBpNXA6gYZUwNKjR2EDgQnufrKQ8iWBaVWUBtaJDbc7ibAURlJeUA\n";
//        String cook ="cf_clearance=Oq62i1bSphIPtXCaI_.2UgTPV2z6M_91qrOXgh3b2C0-1716810611-1.0.1.1-7gD8NMXwcUELCGzakUXhcB3fVWDXSGPe04ofxrsiSDYiSVbj7N4gNW7fM7w4l8KZ.caNxnyTUXaSzvWomD3fIw; __Secure-ab-group=86; __Secure-ext_xcid=226cb4f467503accbabf624ae341f09f; __Secure-ETC=f42656b503d7f3ed95c52092920efc1d; TS0121feed=0187c00a18ae342e2fb64b57973a27629b2d3ecbb9c1dbfd98ba8812a99ea88d2ec94b8f51a1b67d1d09c8a51fd23fc0ca7babf0f6; xcid=07708f6940f33d95a3c732430c5022e0; rfuid=NjkyNDcyNDUyLDEyNC4wNDM0NzUyNzUxNjA3NCwxMDI4MjM3MjIzLC0xLC05ODc0NjQ3MjQsVzNzaWJtRnRaU0k2SWxCRVJpQldhV1YzWlhJaUxDSmtaWE5qY21sd2RHbHZiaUk2SWxCdmNuUmhZbXhsSUVSdlkzVnRaVzUwSUVadmNtMWhkQ0lzSW0xcGJXVlVlWEJsY3lJNlczc2lkSGx3WlNJNkltRndjR3hwWTJGMGFXOXVMM0JrWmlJc0luTjFabVpwZUdWeklqb2ljR1JtSW4wc2V5SjBlWEJsSWpvaWRHVjRkQzl3WkdZaUxDSnpkV1ptYVhobGN5STZJbkJrWmlKOVhYMHNleUp1WVcxbElqb2lRMmh5YjIxbElGQkVSaUJXYVdWM1pYSWlMQ0prWlhOamNtbHdkR2x2YmlJNklsQnZjblJoWW14bElFUnZZM1Z0Wlc1MElFWnZjbTFoZENJc0ltMXBiV1ZVZVhCbGN5STZXM3NpZEhsd1pTSTZJbUZ3Y0d4cFkyRjBhVzl1TDNCa1ppSXNJbk4xWm1acGVHVnpJam9pY0dSbUluMHNleUowZVhCbElqb2lkR1Y0ZEM5d1pHWWlMQ0p6ZFdabWFYaGxjeUk2SW5Ca1ppSjlYWDBzZXlKdVlXMWxJam9pUTJoeWIyMXBkVzBnVUVSR0lGWnBaWGRsY2lJc0ltUmxjMk55YVhCMGFXOXVJam9pVUc5eWRHRmliR1VnUkc5amRXMWxiblFnUm05eWJXRjBJaXdpYldsdFpWUjVjR1Z6SWpwYmV5SjBlWEJsSWpvaVlYQndiR2xqWVhScGIyNHZjR1JtSWl3aWMzVm1abWw0WlhNaU9pSndaR1lpZlN4N0luUjVjR1VpT2lKMFpYaDBMM0JrWmlJc0luTjFabVpwZUdWeklqb2ljR1JtSW4xZGZTeDdJbTVoYldVaU9pSk5hV055YjNOdlpuUWdSV1JuWlNCUVJFWWdWbWxsZDJWeUlpd2laR1Z6WTNKcGNIUnBiMjRpT2lKUWIzSjBZV0pzWlNCRWIyTjFiV1Z1ZENCR2IzSnRZWFFpTENKdGFXMWxWSGx3WlhNaU9sdDdJblI1Y0dVaU9pSmhjSEJzYVdOaGRHbHZiaTl3WkdZaUxDSnpkV1ptYVhobGN5STZJbkJrWmlKOUxIc2lkSGx3WlNJNkluUmxlSFF2Y0dSbUlpd2ljM1ZtWm1sNFpYTWlPaUp3WkdZaWZWMTlMSHNpYm1GdFpTSTZJbGRsWWt0cGRDQmlkV2xzZEMxcGJpQlFSRVlpTENKa1pYTmpjbWx3ZEdsdmJpSTZJbEJ2Y25SaFlteGxJRVJ2WTNWdFpXNTBJRVp2Y20xaGRDSXNJbTFwYldWVWVYQmxjeUk2VzNzaWRIbHdaU0k2SW1Gd2NHeHBZMkYwYVc5dUwzQmtaaUlzSW5OMVptWnBlR1Z6SWpvaWNHUm1JbjBzZXlKMGVYQmxJam9pZEdWNGRDOXdaR1lpTENKemRXWm1hWGhsY3lJNkluQmtaaUo5WFgxZCxXeUp5ZFNKZCwwLDEsMCwyNCwyMzc0MTU5MzAsOCwyMjcxMjY1MjAsMCwxLDIwLC00OTEyNzU1MjMsUjI5dloyeGxJRWx1WXk0Z1RtVjBjMk5oY0dVZ1IyVmphMjhnVjJsdU16SWdOUzR3SUNoWGFXNWtiM2R6SUU1VUlERXdMakE3SUZkcGJqWTBPeUI0TmpRcElFRndjR3hsVjJWaVMybDBMelV6Tnk0ek5pQW9TMGhVVFV3c0lHeHBhMlVnUjJWamEyOHBJRU5vY205dFpTOHhNekF1TUM0d0xqQWdXV0ZDY205M2MyVnlMekkwTGpFeUxqQXVNQ0JUWVdaaGNta3ZOVE0zTGpNMklESXdNRE13TVRBM0lFMXZlbWxzYkdFPSxleUpqYUhKdmJXVWlPbnNpWVhCd0lqcDdJbWx6U1c1emRHRnNiR1ZrSWpwbVlXeHpaU3dpU1c1emRHRnNiRk4wWVhSbElqcDdJa1JKVTBGQ1RFVkVJam9pWkdsellXSnNaV1FpTENKSlRsTlVRVXhNUlVRaU9pSnBibk4wWVd4c1pXUWlMQ0pPVDFSZlNVNVRWRUZNVEVWRUlqb2libTkwWDJsdWMzUmhiR3hsWkNKOUxDSlNkVzV1YVc1blUzUmhkR1VpT25zaVEwRk9UazlVWDFKVlRpSTZJbU5oYm01dmRGOXlkVzRpTENKU1JVRkVXVjlVVDE5U1ZVNGlPaUp5WldGa2VWOTBiMTl5ZFc0aUxDSlNWVTVPU1U1SElqb2ljblZ1Ym1sdVp5SjlmU3dpYVRFNGJpSTZlMzE5TENKNVlXNWtaWGdpT25zaWJXVmthV0VpT250OUxDSnlaV0ZrWVdKcGJHbDBlU0k2ZTMwc0ltNWxkWEp2UVhOemFYTjBZVzUwSWpwN0ltOXVVR0ZuWlVOb1lXNW5aV1FpT250OUxDSnZiazVsZFhKdlFYTnphWE4wWVc1MFQzQmxibVZrU1c1VGNHeHBkRlpwWlhkTllYbGlaVU5vWVc1blpXUWlPbnQ5ZlN3aWNIVmliR2xqUm1WaGRIVnlaU0k2ZXlKVWRYSmliMEZ3Y0ZOMFlYUmxJanA3SWtoQlUxOUNSVlJVUlZKZlZrVlNVMGxQVGlJNkltaGhjMEpsZEhSbGNsWmxjbk5wYjI0aUxDSkpUbDlRVWs5SFVrVlRVeUk2SW1sdVVISnZaM0psYzNNaUxDSkpUbE5VUVV4TVFWUkpUMDVmUlZKU1QxSWlPaUpwYm5OMFlXeHNZWFJwYjI1RmNuSnZjaUlzSWs1QlZrbEhRVlJKVDA1ZlZFOWZWVTVMVGs5WFRsOUJVRkJNU1VOQlZFbFBUaUk2SW01aGRtbG5ZWFJwYjI1VWIxVnVhMjV2ZDI1QmNIQnNhV05oZEdsdmJpSXNJazVQVkY5SlRsTlVRVXhNUlVRaU9pSnViM1JKYm5OMFlXeHNaV1FpTENKU1JVRkVXVjlHVDFKZlZWTkZJam9pY21WaFpIbEdiM0pWYzJVaWZYMTlmUT09LDY1LC0xMjg1NTUxMywxLDEsLTEsMTY5OTk1NDg4NywxNjk5OTU0ODg3LDMzNjAzOTE3NSw4; ADDRESSBOOKBAR_WEB_CLARIFICATION=1737660060; guest=true; TS018529d3=0187c00a18b851aa786fb62adc2e95625856dea7be8248a3ce01a75db604b26df22d15cee234b7512e4c6065724e49f14d2b7392dc; __Secure-access-token=7.58130011.U3d2aWE9RxWjq-SZ-s2BFw.86.AWzUTYNXtsZZ_ZgyzVSTF_e-YXnyRA6ndnJ8qH6-ny7adQeI3ice8mypr7HOz1XQkI6NIEugdpqQSh3yP0Twspk.20210719080015.20250123212252.cIjnUmJCN5AhotOd28o8BNeZm52v5enEh657M3cLhss.144408c96b23d340d; __Secure-refresh-token=7.58130011.U3d2aWE9RxWjq-SZ-s2BFw.86.AWzUTYNXtsZZ_ZgyzVSTF_e-YXnyRA6ndnJ8qH6-ny7adQeI3ice8mypr7HOz1XQkI6NIEugdpqQSh3yP0Twspk.20210719080015.20250123212252.d8t-LtVe7JCNua1IH6NiE7gBXs5RPhbuTHUcZeKjTw4.1268790fad1a0c09c; __Secure-user-id=58130011; is_adult_confirmed=; is_alco_adult_confirmed=; ozonIdAuthResponseToken=eyJhbGciOiJIUzI1NiIsIm96b25pZCI6Im5vdHNlbnNpdGl2ZSIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjo1ODEzMDAxMSwiaXNfcmVnaXN0cmF0aW9uIjpmYWxzZSwicmV0dXJuX3VybCI6Ii8iLCJwYXlsb2FkIjpudWxsLCJleHAiOjE3Mzc2NjAxODIsImlhdCI6MTczNzY2MDE3MiwiaXNzIjoib3pvbmlkIn0.eDTsR868a4zLTqh_x5UmxXwj_vg54NV8iEySTuGZVG4; TS0149423d=0187c00a186d8e19d0ff26d6c06691c8147065b4d1d5b5bde8ebe3f7d41b1619fbbaf4f2084bf436ca0221a6bb1ca90c85e93a0bf6; abt_data=7.0kwGEAmEU8nzOyF3cZyIOnpKjA6JXJAml9ThaBNhDQyl10RcWT7k4eX66sf_LyM9BgpA8uQtQkIyFJZnDJ0pdW2i6QZe1jaQrEBBFBbocD-WEFOlyooumk0tR6oXZ5ywTGmIMqEbkhB6xVEGGeTuyJ0zdn8lKhHOJ5wklLtX_wgK33PLN_EJRwDN-Im5De26Ev-otzTUfQY4-XvNNd1-5FwEpcBm_nZxadgn0eMaEOhaBPZulooc7gf46AsQh8Ld5tiO7KZ-4RQQ56s37CFG7sT1iYvuSC7NQfhaey9ln_OKPJSCCg503-iPrPbqAHmuSaOojN8023E_AYQFsCPue_921lvT8L6FpjUD7_3EqGrCSN4VGY8fLkQyKQqPgFCYfqiqv2LbKstFruVu7KaUz5lW3BX1bZDqalrRDnjegSi7WGdcuiSkzfaLMUaF0cusDjnlzz3QQmLN1qY6WizrH1D2grX6_IIdMYh50rAVFEGVKXW2t0cBGgqRm7WDae72jOs_cGNCCV9v\n";
//        String cook = "";
        String cook = "ob_theme=DEFAULT; __Secure-ext_xcid=2de4deb43ba86ea2567b884cd698c14f; cf_clearance=kHKvtC94NLJHFrnRyEPenNhDEnwwkEbXBuDy0G6OpWo-1715163523-1.0.1.1-kq87Y8WOqAoqYOqNgtIUDsAgF0xBUPY5MDVkXB_4Ek4iP6hleK8ATs2wrik22b1OzSS6gRqm2Fk7SjZPps4cXQ; __Secure-ab-group=94; cf_clearance=O_vIHKyJNvlCbE8fjCcb38rJFo39pSMSZ74QzBVKL5Q-1717425520-1.0.1.1-vCfWiM8Vhk_jcoPZYHg83EaXxYXLkhBJFlzZGafM9r0KxmVqICP5dzSnIeD57dhVjVgHQu475RSthsChbsATGQ; abt_data=7.ueB896wNScSzsyh7P2Fpk0-zN8_7oBsweUP2o7_IQ5MziASSq_09PfuPcilsVVLhwdbqY27dNBheBny-JcaiH9kGZhXCbQq4PrYV0oYfXq4svysU_Rb2n0yC5gsm4wVQmjJzdU1PJ1dv4XCkzMFsRru9fINsaToFD1kDpRXLDFgWI4-d2CYwY1PknRNRuVAkh27Yj0FQIW1Ta8l8a96n1bZaDYUiWvOhDZpCjK6yVdKQsDQ8ClhNJ_CmG3oheavEBTCkDPGVpmsOlJoXVPh1O1uTI625L8FoEIY6vsN-M682QWm9nJ1-Ax3rE6pysYZ1aZf1SwaxYYzp8Ex7hILksvSQjtKuPIgWhcO0YJy4ydhohY0wkpLrjxZfOeWjnkxhU5q5aRmkKz6UznufJVr-WiU-SwAgCg11dVjuRNFOE16keWuo3r5p54KgKv_L3o6W64gj_zGYD5iAHWhFKMAqktJ92zzthh_7ZJBxsRQQt9a6hOdi4qEmBCqkGfIUoFkHedZdTQPJ4g; ADDRESSBOOKBAR_WEB_CLARIFICATION=1737444542; __Secure-ETC=a7f3da441dc0255f38baa7604b666a29; rfuid=NjkyNDcyNDUyLDEyNC4wNDM0NzUyNzUxNjA3NCwxMDI4MjM3MjIzLC0xLDE2NTY2MzkxODEsVzNzaWJtRnRaU0k2SWxCRVJpQldhV1YzWlhJaUxDSmtaWE5qY21sd2RHbHZiaUk2SWxCdmNuUmhZbXhsSUVSdlkzVnRaVzUwSUVadmNtMWhkQ0lzSW0xcGJXVlVlWEJsY3lJNlczc2lkSGx3WlNJNkltRndjR3hwWTJGMGFXOXVMM0JrWmlJc0luTjFabVpwZUdWeklqb2ljR1JtSW4wc2V5SjBlWEJsSWpvaWRHVjRkQzl3WkdZaUxDSnpkV1ptYVhobGN5STZJbkJrWmlKOVhYMHNleUp1WVcxbElqb2lRMmh5YjIxbElGQkVSaUJXYVdWM1pYSWlMQ0prWlhOamNtbHdkR2x2YmlJNklsQnZjblJoWW14bElFUnZZM1Z0Wlc1MElFWnZjbTFoZENJc0ltMXBiV1ZVZVhCbGN5STZXM3NpZEhsd1pTSTZJbUZ3Y0d4cFkyRjBhVzl1TDNCa1ppSXNJbk4xWm1acGVHVnpJam9pY0dSbUluMHNleUowZVhCbElqb2lkR1Y0ZEM5d1pHWWlMQ0p6ZFdabWFYaGxjeUk2SW5Ca1ppSjlYWDBzZXlKdVlXMWxJam9pUTJoeWIyMXBkVzBnVUVSR0lGWnBaWGRsY2lJc0ltUmxjMk55YVhCMGFXOXVJam9pVUc5eWRHRmliR1VnUkc5amRXMWxiblFnUm05eWJXRjBJaXdpYldsdFpWUjVjR1Z6SWpwYmV5SjBlWEJsSWpvaVlYQndiR2xqWVhScGIyNHZjR1JtSWl3aWMzVm1abWw0WlhNaU9pSndaR1lpZlN4N0luUjVjR1VpT2lKMFpYaDBMM0JrWmlJc0luTjFabVpwZUdWeklqb2ljR1JtSW4xZGZTeDdJbTVoYldVaU9pSk5hV055YjNOdlpuUWdSV1JuWlNCUVJFWWdWbWxsZDJWeUlpd2laR1Z6WTNKcGNIUnBiMjRpT2lKUWIzSjBZV0pzWlNCRWIyTjFiV1Z1ZENCR2IzSnRZWFFpTENKdGFXMWxWSGx3WlhNaU9sdDdJblI1Y0dVaU9pSmhjSEJzYVdOaGRHbHZiaTl3WkdZaUxDSnpkV1ptYVhobGN5STZJbkJrWmlKOUxIc2lkSGx3WlNJNkluUmxlSFF2Y0dSbUlpd2ljM1ZtWm1sNFpYTWlPaUp3WkdZaWZWMTlMSHNpYm1GdFpTSTZJbGRsWWt0cGRDQmlkV2xzZEMxcGJpQlFSRVlpTENKa1pYTmpjbWx3ZEdsdmJpSTZJbEJ2Y25SaFlteGxJRVJ2WTNWdFpXNTBJRVp2Y20xaGRDSXNJbTFwYldWVWVYQmxjeUk2VzNzaWRIbHdaU0k2SW1Gd2NHeHBZMkYwYVc5dUwzQmtaaUlzSW5OMVptWnBlR1Z6SWpvaWNHUm1JbjBzZXlKMGVYQmxJam9pZEdWNGRDOXdaR1lpTENKemRXWm1hWGhsY3lJNkluQmtaaUo5WFgxZCxXeUp5ZFMxU1ZTSmQsMCwxLDAsMjQsMjM3NDE1OTMwLDgsMjI3MTI2NTIwLDAsMSwyMCwtNDkxMjc1NTIzLFIyOXZaMnhsSUVsdVl5NGdUbVYwYzJOaGNHVWdSMlZqYTI4Z1YybHVNeklnTlM0d0lDaFhhVzVrYjNkeklFNVVJREV3TGpBN0lGZHBialkwT3lCNE5qUXBJRUZ3Y0d4bFYyVmlTMmwwTHpVek55NHpOaUFvUzBoVVRVd3NJR3hwYTJVZ1IyVmphMjhwSUVOb2NtOXRaUzh4TXpJdU1DNHdMakFnVTJGbVlYSnBMelV6Tnk0ek5pQXlNREF6TURFd055Qk5iM3BwYkd4aCxleUpqYUhKdmJXVWlPbnNpWVhCd0lqcDdJbWx6U1c1emRHRnNiR1ZrSWpwbVlXeHpaU3dpU1c1emRHRnNiRk4wWVhSbElqcDdJa1JKVTBGQ1RFVkVJam9pWkdsellXSnNaV1FpTENKSlRsTlVRVXhNUlVRaU9pSnBibk4wWVd4c1pXUWlMQ0pPVDFSZlNVNVRWRUZNVEVWRUlqb2libTkwWDJsdWMzUmhiR3hsWkNKOUxDSlNkVzV1YVc1blUzUmhkR1VpT25zaVEwRk9UazlVWDFKVlRpSTZJbU5oYm01dmRGOXlkVzRpTENKU1JVRkVXVjlVVDE5U1ZVNGlPaUp5WldGa2VWOTBiMTl5ZFc0aUxDSlNWVTVPU1U1SElqb2ljblZ1Ym1sdVp5SjlmWDE5LDY1LC0xMjg1NTUxMywxLDEsLTEsMTY5OTk1NDg4NywxNjk5OTU0ODg3LDMzNjAzOTE3NSw4; __Secure-user-id=58130011; abt_data=7.GbOLVSYzDBPM-VWjPYdygA-fmt2kPPFrnGmevftayN-s4Yn9wPvyUvOoUK5Eurnjd7IIsAGnuRJIu2ajOy65in_vP5pdT8gabfnjrHPmONQvMCf1YbWIv6DZFh7r4vwMP2_akoPet0jaWTCjddXXP7ZN59cHqGTyDOXs4hR3scrnRcGqktDN-iy2TpnNbPcBbG7o2aeLv9RXS_BIVjfitUE_m2FXtOzcZoNsVpSFf9dSGozN73_90lU0tdHZ50cpePZEWDXxN1NmfZYG2kOOZsWdEbBqAmUD21g3_4QDiQoVrhvLy068h8nxTQG0jKEdRjZXZ2RLpJbH1qu15eCZCIHvMASM6ujbGFhPePDsbGiap_MzM8Wjn_1SbBcVlaE5SEb_ht_pwKhpJv_r-aHwZgy-8q5ro2rKIeFUa4yrM5zJFZfk1aifeoOnrLcUHhhftIivzGc1J8Z8fdG4141npjOOiDXFN4W9ghg0OtVeSsKNpouiK7qkay0u3-jqe97GzF-uDNshnH3jTeI; TS0121feed=0187c00a1813c01804868493374cd408fb6a8ef92173e44482690f97528cd790c0c324bb2d471cfe9d66e8bb60d3a73ea4708a1a07; xcid=e26dc4408cc7c8a970c9374d08937625; is_cookies_accepted=1; __Secure-access-token=7.58130011.hBnr2ghHS0idlA2HRcxGNA.94.AQdSwnm-c3PLVJ2NZtm5Hg0augGKeDjVX57oskcvmdQ7CzjeYpT83UngFXgBcLG_9M9L6XiFWvP_ORhxbnGP_70.20210719080015.20250124082058.98D2O6b5JtZXWyU92IV6NgcdHVnAE1d2wHicvpnRLLs.18bf1fb8e38da9f45; __Secure-refresh-token=7.58130011.hBnr2ghHS0idlA2HRcxGNA.94.AQdSwnm-c3PLVJ2NZtm5Hg0augGKeDjVX57oskcvmdQ7CzjeYpT83UngFXgBcLG_9M9L6XiFWvP_ORhxbnGP_70.20210719080015.20250124082058.2TyiZb2vKO1qAdlXxMIA5SP4-sqiQadzCuYAdkNUM9g.114e4f69cd192a9dd; TS0149423d=0187c00a183878226826b940d027fd214870b14e74024e67d399c2c608c5b0edc54bb10d42c08556405fb1db36ad91e65b0bc60a45";
        try {
            // URL страницы для парсинга
            String url = "https://www.ozon.ru/my/orderlist";

            // Открываем страницу для добавления cookies
            driver.get("https://www.ozon.ru"); // Главная страница сайта (или любая доступная)

            // Добавляем cookies вручную (замените своими значениями)
            driver.manage().deleteAllCookies(); // Очистить существующие cookies
            Map<String, String> cookies = getCookies(cook);
            getOrders(cookies);
            System.exit(0);
            cookies.entrySet().stream()
                    .forEach(es -> driver.manage().addCookie(new Cookie(
                            es.getKey(), es.getValue())));

//            driver.manage().addCookie(new Cookie("ADDRESSBOOKBAR_WEB_CLARIFICATION", "1737444542"));
//            driver.manage().addCookie(new Cookie("TS0121feed", "0187c00a18e734dcdbb433d3fe5e9416c226962e939dd1f3cd387bae9fa0648d0f597bce42c094af84f9d80233388017d213210adc"));
//            driver.manage().addCookie(new Cookie("TS0149423d", "0187c00a18460ca3a4af33f7ce70c7ca6d962b3e5de6a2fde70755bea47ef9cf4aab562f73a5c542bacb394c261f721ee02ee03f34"));
//            driver.manage().addCookie(new Cookie("__Secure-ETC", "a7f3da441dc0255f38baa7604b666a29"));
//            driver.manage().addCookie(new Cookie("__Secure-ab-group", "94"));
//
//            driver.manage().addCookie(new Cookie("__Secure-access-token", "7.58130011.hBnr2ghHS0idlA2HRcxGNA.94.AR1aHo6hx4eJj95hmBY-i0r_TyGdwpZk8nzuq1vGE2RZQu5Vxqly6sn_mj2x4Ha8FAMEwrjQ8QZ75ew6SovpER4.20210719080015.20250123205836.MaLOXOwpp02qiTjmweyOO-wKgEnRU-EdGtqiRez54Fw.1eb69a92fc628d21f"));
//            driver.manage().addCookie(new Cookie("__Secure-ext_xcid", "2de4deb43ba86ea2567b884cd698c14f"));
//            driver.manage().addCookie(new Cookie("__Secure-refresh-token", "7.58130011.hBnr2ghHS0idlA2HRcxGNA.94.AR1aHo6hx4eJj95hmBY-i0r_TyGdwpZk8nzuq1vGE2RZQu5Vxqly6sn_mj2x4Ha8FAMEwrjQ8QZ75ew6SovpER4.20210719080015.20250123205836.utde7rXSyx7vEwoy4yt6zTRNCMRNGoqU9orfyLixBTA.18755f1002d92e87e"));
//            driver.manage().addCookie(new Cookie("__Secure-user-id", "0"));
//            driver.manage().addCookie(new Cookie("abt_data", "7.PjEAPjZYYutAJxSf-vnAgnfGvVEvt_AeRAOayqcFJqdT6dDSMIsS8yXzp2lYLGXbfsT9aWjxZc2DRsRBqFOk-LZT_JTQpJr1uQwfGQhkftGrnspaztyOdjwaE-fg0Inapg_QZgWyZLXivX6gUZHuOP-1e_8V-VM5__EWn8w9ahnn3xD7BmVQi5uVIrn_R-Zm_XRo1TgCbS9iJ-7sjgGYCeIY2DlN6sVC5iSguxz0-ZWmcXY2XZf7D1AkxKpcVJwFomtFqlYnkmQQeB7AUkxn0K9qwDik4tGVeb3pFYJpMur58R0fsDJjsKS6SujVLCaRNAuJWDyI3Zr9eOuo"));
//            driver.manage().addCookie(new Cookie("abt_data", "7.lgdTqfwA8ApUKNZYBTFZgf_7c1R6i1xZA20iyobiwk0EqgBnG74jzYqCuigySUez2UL6meEhuhMwsvGwwyRknO8bDwA2YKIXbWzl0fidXuOVnWb-L0yff9lJrIo66iBu7CVLl_ebG6tg4HcHPyNEQBGGA5LivkcCVyIvblkaZecCS3nDMJomFyHvTKDAepYS7vwXVOGiVjwavhP3sropmyu26xMD_kLBOBU_ul1oTE7HAIRYeG-G_Eidn2Kvp9Um79iMaB5U2VPz7Ao1CD4NzvcQ3rrHc9PhEycSYJmp2wkfzymmEqfbjtlZe2LyVgBgxiGDvp1ph-jWU6b73bI6C6bDhqjOTBk"));
//            driver.manage().addCookie(new Cookie("abt_data", "7.ueB896wNScSzsyh7P2Fpk0-zN8_7oBsweUP2o7_IQ5MziASSq_09PfuPcilsVVLhwdbqY27dNBheBny-JcaiH9kGZhXCbQq4PrYV0oYfXq4svysU_Rb2n0yC5gsm4wVQmjJzdU1PJ1dv4XCkzMFsRru9fINsaToFD1kDpRXLDFgWI4-d2CYwY1PknRNRuVAkh27Yj0FQIW1Ta8l8a96n1bZaDYUiWvOhDZpCjK6yVdKQsDQ8ClhNJ_CmG3oheavEBTCkDPGVpmsOlJoXVPh1O1uTI625L8FoEIY6vsN-M682QWm9nJ1-Ax3rE6pysYZ1aZf1SwaxYYzp8Ex7hILksvSQjtKuPIgWhcO0YJy4ydhohY0wkpLrjxZfOeWjnkxhU5q5aRmkKz6UznufJVr-WiU-SwAgCg11dVjuRNFOE16keWuo3r5p54KgKv_L3o6W64gj_zGYD5iAHWhFKMAqktJ92zzthh_7ZJBxsRQQt9a6hOdi4qEmBCqkGfIUoFkHedZdTQPJ4g"));
//            driver.manage().addCookie(new Cookie("abt_data", "7.BEc5l7HCZ8uJbuvoyyWhyhYC2PcIf4IuJqZvXzQj7bW8ssk9fOpRIrGPK_AUqJP2v6yRIiFfsbOFMujwhfzYg41uAeouAYWYwH0z6goeNDml2Iw7VymhLzHi3bSg2_N2E52A3svWZdKDCpKLSv-QF8tSJIoc_Z5o9KGijSZcUE1FftpdiPA-K--KSEdSb_8aMoN2sCo-OjYw_NR1yQIiHRBmzynqGtO23n-OV3zj7A868GAtDVykTxvndc-o1nvXTZeFfx8cgU11lg8TUQ_r5zVASVC-QI-rdFpiT4H2Sny0YWQS84DeA75vBHOEZ8Tmmz0S9-WN0a_CvAyzpH0a4DBsoSrbw-WmlyiwIlu0C6UPzYVuGArSmn_3ufTB10eW5-gR8EZXdDzhPubWBRjxhili6Mf6fHGaJblSZNju64FOR4Lv7f4L-XscyVzDXfBnlDj_uesNtJMxgbFEdPPJgVcfy7F83ZnqtOy6OUWsyn0oBwTsjRa0gdtUAeBVCvmcatFIvbVq"));
//            driver.manage().addCookie(new Cookie("abt_data", "7.ri_HoQ8mg3Qmk2TGmp0lsf96ONvHzXy97_FcWQaaj1jCB43liovKS22DnUBqF4fXneqohKE0lbuEIWJ8CPy8zPeKilexFTRjRrDEMkBQufF9WInlQN6ksdFzvvlXRj1v7IBDcLbHsgiuigYzf0YFGHBIjnRtT9uYgjV69VGC50ltv6R-b_niGoEgeuGpRztySQ-XgNZbreO25-3BLcWrbkAIf7zJhE1WKoCtLH35Hw-3n8_vBvGL9DtQSCGGXXi-Hb6n7NX2MpVJY0KApz5VUTJd4J6YGgwMTkK8x2e5Uv-7Ia-2IQ"));
//            driver.manage().addCookie(new Cookie("ob_theme", "DEFAULT"));
//            driver.manage().addCookie(new Cookie("cf_clearance", "kHKvtC94NLJHFrnRyEPenNhDEnwwkEbXBuDy0G6OpWo-1715163523-1.0.1.1-kq87Y8WOqAoqYOqNgtIUDsAgF0xBUPY5MDVkXB_4Ek4iP6hleK8ATs2wrik22b1OzSS6gRqm2Fk7SjZPps4cXQ"));
//            driver.manage().addCookie(new Cookie("cf_clearance", "O_vIHKyJNvlCbE8fjCcb38rJFo39pSMSZ74QzBVKL5Q-1717425520-1.0.1.1-vCfWiM8Vhk_jcoPZYHg83EaXxYXLkhBJFlzZGafM9r0KxmVqICP5dzSnIeD57dhVjVgHQu475RSthsChbsATGQ"));
//            driver.manage().addCookie(new Cookie("xcid", "f85f8acc74dc15518814d6b89bcaae62"));
//            driver.manage().addCookie(new Cookie("rfuid", "NjkyNDcyNDUyLDEyNC4wNDM0NzUyNzUxNjA3NCwxMDI4MjM3MjIzLC0xLC0xOTAwMDQ5ODE1LFczc2libUZ0WlNJNklsQkVSaUJXYVdWM1pYSWlMQ0prWlhOamNtbHdkR2x2YmlJNklsQnZjblJoWW14bElFUnZZM1Z0Wlc1MElFWnZjbTFoZENJc0ltMXBiV1ZVZVhCbGN5STZXM3NpZEhsd1pTSTZJbUZ3Y0d4cFkyRjBhVzl1TDNCa1ppSXNJbk4xWm1acGVHVnpJam9pY0dSbUluMHNleUowZVhCbElqb2lkR1Y0ZEM5d1pHWWlMQ0p6ZFdabWFYaGxjeUk2SW5Ca1ppSjlYWDBzZXlKdVlXMWxJam9pUTJoeWIyMWxJRkJFUmlCV2FXVjNaWElpTENKa1pYTmpjbWx3ZEdsdmJpSTZJbEJ2Y25SaFlteGxJRVJ2WTNWdFpXNTBJRVp2Y20xaGRDSXNJbTFwYldWVWVYQmxjeUk2VzNzaWRIbHdaU0k2SW1Gd2NHeHBZMkYwYVc5dUwzQmtaaUlzSW5OMVptWnBlR1Z6SWpvaWNHUm1JbjBzZXlKMGVYQmxJam9pZEdWNGRDOXdaR1lpTENKemRXWm1hWGhsY3lJNkluQmtaaUo5WFgwc2V5SnVZVzFsSWpvaVEyaHliMjFwZFcwZ1VFUkdJRlpwWlhkbGNpSXNJbVJsYzJOeWFYQjBhVzl1SWpvaVVHOXlkR0ZpYkdVZ1JHOWpkVzFsYm5RZ1JtOXliV0YwSWl3aWJXbHRaVlI1Y0dWeklqcGJleUowZVhCbElqb2lZWEJ3YkdsallYUnBiMjR2Y0dSbUlpd2ljM1ZtWm1sNFpYTWlPaUp3WkdZaWZTeDdJblI1Y0dVaU9pSjBaWGgwTDNCa1ppSXNJbk4xWm1acGVHVnpJam9pY0dSbUluMWRmU3g3SW01aGJXVWlPaUpOYVdOeWIzTnZablFnUldSblpTQlFSRVlnVm1sbGQyVnlJaXdpWkdWelkzSnBjSFJwYjI0aU9pSlFiM0owWVdKc1pTQkViMk4xYldWdWRDQkdiM0p0WVhRaUxDSnRhVzFsVkhsd1pYTWlPbHQ3SW5SNWNHVWlPaUpoY0hCc2FXTmhkR2x2Ymk5d1pHWWlMQ0p6ZFdabWFYaGxjeUk2SW5Ca1ppSjlMSHNpZEhsd1pTSTZJblJsZUhRdmNHUm1JaXdpYzNWbVptbDRaWE1pT2lKd1pHWWlmVjE5TEhzaWJtRnRaU0k2SWxkbFlrdHBkQ0JpZFdsc2RDMXBiaUJRUkVZaUxDSmtaWE5qY21sd2RHbHZiaUk2SWxCdmNuUmhZbXhsSUVSdlkzVnRaVzUwSUVadmNtMWhkQ0lzSW0xcGJXVlVlWEJsY3lJNlczc2lkSGx3WlNJNkltRndjR3hwWTJGMGFXOXVMM0JrWmlJc0luTjFabVpwZUdWeklqb2ljR1JtSW4wc2V5SjBlWEJsSWpvaWRHVjRkQzl3WkdZaUxDSnpkV1ptYVhobGN5STZJbkJrWmlKOVhYMWQsV3lKeWRTMVNWU0pkLDAsMSwwLDI0LDIzNzQxNTkzMCw4LDIyNzEyNjUyMCwwLDEsMjAsLTQ5MTI3NTUyMyxSMjl2WjJ4bElFbHVZeTRnVG1WMGMyTmhjR1VnUjJWamEyOGdWMmx1TXpJZ05TNHdJQ2hYYVc1a2IzZHpJRTVVSURFd0xqQTdJRmRwYmpZME95QjROalFwSUVGd2NHeGxWMlZpUzJsMEx6VXpOeTR6TmlBb1MwaFVUVXdzSUd4cGEyVWdSMlZqYTI4cElFTm9jbTl0WlM4eE16RXVNQzR3TGpBZ1UyRm1ZWEpwTHpVek55NHpOaUF5TURBek1ERXdOeUJOYjNwcGJHeGgsZXlKamFISnZiV1VpT25zaVlYQndJanA3SW1selNXNXpkR0ZzYkdWa0lqcG1ZV3h6WlN3aVNXNXpkR0ZzYkZOMFlYUmxJanA3SWtSSlUwRkNURVZFSWpvaVpHbHpZV0pzWldRaUxDSkpUbE5VUVV4TVJVUWlPaUpwYm5OMFlXeHNaV1FpTENKT1QxUmZTVTVUVkVGTVRFVkVJam9pYm05MFgybHVjM1JoYkd4bFpDSjlMQ0pTZFc1dWFXNW5VM1JoZEdVaU9uc2lRMEZPVGs5VVgxSlZUaUk2SW1OaGJtNXZkRjl5ZFc0aUxDSlNSVUZFV1Y5VVQxOVNWVTRpT2lKeVpXRmtlVjkwYjE5eWRXNGlMQ0pTVlU1T1NVNUhJam9pY25WdWJtbHVaeUo5ZlgxOSw2NSwtMTI4NTU1MTMsMSwxLC0xLDE2OTk5NTQ4ODcsMTY5OTk1NDg4NywzMzYwMzkxNzUsOA=="));


            // Переходим к защищенной странице
            driver.get(url);

            // Ждем загрузки страницы (опционально)
            Thread.sleep(3000); // Замените на WebDriverWait, если требуется точный контроль

            // Получаем HTML-код страницы
//            String pageSource = driver.getPageSource();
//            System.out.println(pageSource);


            WebElement paginator = driver.findElement(By.cssSelector("div[data-widget='paginator']"));
            String paginatorText = paginator.getText();
            System.out.println(paginatorText);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            // Закрываем браузер
//            driver.quit();
        }
    }


    public static Map<String, String> getCookies(String cookie) {
        Map<String, String> result = new HashMap<>();
        String[] split = cookie.split(";");
        for (String el : split) {
            int i = el.indexOf("=");
            String key = el.substring(0, i);
            String value = el.substring(i + 1, el.length());
            result.put(key.trim(), value.trim());
        }
        return result;

    }

    public static void getOrders(Map<String, String> cookies) {
        // URL страницы Ozon
        String url = "https://www.ozon.ru/my/orderlist";
        try {
            // Устанавливаем соединение с сайтом, передавая cookies
            Connection connection = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.5615.138 Safari/537.36")
                    .timeout(10_000)
                    .cookies(cookies)
                    .followRedirects(true);

            // Загружаем страницу
            Document document = connection.get();

            // Находим <section> по атрибуту data-widget="orderList"
            Element section = document.selectFirst("section[data-widget=orderList]");

            if (section != null) {
                // Получаем все вложенные <div> внутри найденной секции
                Elements divs = section.select("> div");

                // Выводим содержимое найденных <div>
                for (Element div : divs) {
                    Order order = getOrder(div);
                    System.out.println(order);
                    order.getPositions().forEach(System.out::println);
                    System.out.println("-----------------------------------------");
                }
            } else {
                System.out.println("Секция не найдена.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка подключения или парсинга страницы.");
        }
    }

    public static Order getOrder(Element element) {
        Order order = new Order();
        List<Position> positions = new ArrayList<>();
        Elements orderDivs = element.select("> div");
        Element titleDiv = orderDivs.get(0);

        Elements number = titleDiv.select("a");
        order.setHref("https://www.ozon.ru" + number.attr("href"));
        order.setNumber(number.text().trim());

        Elements titleSpanInner = titleDiv.select("span");
        order.setDateOf(titleSpanInner.get(0).text().trim());
        order.setStatus(titleSpanInner.get(1).text().trim());
        order.setPrice(titleSpanInner.get(2).text().trim());


        Element positionDivFull = orderDivs.get(1);
        Elements positionDivs = positionDivFull.select("> div");
        for (Element positionDiv : positionDivs) {
            Position position = getPosition(positionDiv);
            positions.add(position);
        }
        order.setPositions(positions);
        return order;
    }


    public static Position getPosition(Element positionDiv) {
        Position position = new Position();
        Elements positionDivs = positionDiv.select("span");
        Elements positionDateTo = positionDiv.select("p");
        position.setStatus(positionDivs.get(1).text().trim());
        position.setDateTo(positionDateTo.text().trim());
        return position;
    }
}
