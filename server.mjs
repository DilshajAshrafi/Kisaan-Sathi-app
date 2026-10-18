import { createServer } from 'node:http';
import { readFileSync, existsSync } from 'node:fs';
import { join } from 'node:path';

const port = Number(process.env.DEFAULT_APP_PORT || 3000);
const geminiApiKey = process.env.GEMINI_API_KEY || '';

const htmlContent = `<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
  <title>Kisaan Sathi | Smart Farming Support</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&display=swap" rel="stylesheet">
  <style>
    :root {
      --primary: #124D39;
      --primary-dark: #0B3B2A;
      --primary-light: #EBF7EF;
      --accent-gold: #D9A441;
      --gold-light: #FFF5D9;
      --bg: #F8FAF7;
      --card-bg: #FFFFFF;
      --text: #1D2A22;
      --text-muted: #5D6D64;
      --border: rgba(18, 77, 57, 0.12);
      --shadow: 0 4px 18px rgba(11, 59, 42, 0.08);
      --font: 'Plus Jakarta Sans', -apple-system, BlinkMacSystemFont, sans-serif;
    }

    [data-theme="dark"] {
      --primary: #62C587;
      --primary-dark: #124D39;
      --primary-light: #1A3125;
      --accent-gold: #E6C88D;
      --gold-light: #3E3219;
      --bg: #111A15;
      --card-bg: #19251F;
      --text: #E7EEE9;
      --text-muted: #A8B8B0;
      --border: rgba(255, 255, 255, 0.1);
      --shadow: 0 4px 20px rgba(0, 0, 0, 0.4);
    }

    * { box-sizing: border-box; margin: 0; padding: 0; font-family: var(--font); }
    body { background: var(--bg); color: var(--text); min-height: 100vh; display: flex; justify-content: center; }

    .app-viewport {
      width: 100%;
      max-width: 480px;
      min-height: 100vh;
      background: var(--bg);
      display: flex;
      flex-direction: column;
      position: relative;
      box-shadow: 0 0 40px rgba(0,0,0,0.06);
    }

    /* Top App Bar */
    .top-bar {
      position: sticky;
      top: 0;
      z-index: 50;
      background: var(--primary-dark);
      color: #FFF;
      padding: 12px 16px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      box-shadow: 0 2px 10px rgba(0,0,0,0.15);
    }
    .brand-group { display: flex; align-items: center; gap: 10px; }
    .brand-emblem {
      width: 36px;
      height: 36px;
      border-radius: 10px;
      background: linear-gradient(135deg, var(--accent-gold), var(--primary));
      display: grid;
      place-items: center;
      font-size: 1.2rem;
    }
    .brand-title { font-size: 1.15rem; font-weight: 800; letter-spacing: 0.3px; }
    .brand-subtitle { font-size: 0.72rem; color: var(--accent-gold); font-weight: 600; }
    .top-actions { display: flex; align-items: center; gap: 8px; }
    .icon-btn {
      width: 36px;
      height: 36px;
      border-radius: 50%;
      border: none;
      background: rgba(255,255,255,0.12);
      color: #FFF;
      display: grid;
      place-items: center;
      cursor: pointer;
      font-size: 1rem;
      transition: all 0.2s;
    }
    .icon-btn:hover { background: rgba(255,255,255,0.22); }
    .call-btn { background: #E53935; color: #FFF; }
    .call-btn:hover { background: #D32F2F; }

    /* Main Container */
    .screen-content {
      flex: 1;
      padding: 16px;
      padding-bottom: 90px;
      overflow-y: auto;
    }

    /* Bottom Navigation Bar */
    .bottom-bar {
      position: fixed;
      bottom: 0;
      width: 100%;
      max-width: 480px;
      height: 64px;
      background: var(--card-bg);
      border-top: 1px solid var(--border);
      display: flex;
      align-items: center;
      justify-content: space-around;
      z-index: 50;
      box-shadow: 0 -4px 16px rgba(0,0,0,0.06);
    }
    .nav-item {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 3px;
      color: var(--text-muted);
      cursor: pointer;
      font-size: 0.72rem;
      font-weight: 600;
      padding: 6px 10px;
      border-radius: 12px;
      transition: all 0.2s;
      border: none;
      background: transparent;
    }
    .nav-item.active {
      color: var(--primary);
      background: var(--primary-light);
    }
    .nav-icon { font-size: 1.25rem; }

    /* Common Card Styles */
    .card {
      background: var(--card-bg);
      border: 1px solid var(--border);
      border-radius: 20px;
      padding: 18px;
      margin-bottom: 14px;
      box-shadow: var(--shadow);
    }

    .hero-card {
      background: linear-gradient(135deg, #0B3B2A, #1F6A4F);
      color: #FFF;
      border: none;
    }
    .hero-badge {
      display: inline-flex;
      align-items: center;
      gap: 6px;
      background: rgba(255,255,255,0.14);
      padding: 4px 12px;
      border-radius: 999px;
      font-size: 0.75rem;
      font-weight: 700;
      color: var(--accent-gold);
      margin-bottom: 10px;
    }
    .hero-title { font-size: 1.35rem; font-weight: 800; line-height: 1.3; margin-bottom: 6px; }
    .hero-desc { font-size: 0.82rem; color: rgba(255,255,255,0.9); line-height: 1.5; margin-bottom: 16px; }

    .btn {
      display: inline-flex;
      align-items: center;
      justify-content: center;
      gap: 8px;
      padding: 10px 20px;
      border-radius: 999px;
      font-weight: 700;
      font-size: 0.85rem;
      border: none;
      cursor: pointer;
      transition: all 0.2s;
    }
    .btn-gold { background: var(--accent-gold); color: #FFF; }
    .btn-gold:hover { background: #C89435; }
    .btn-primary { background: var(--primary); color: #FFF; }
    .btn-primary:hover { opacity: 0.92; }
    .btn-full { width: 100%; }

    .alert-card {
      background: #E5F3FB;
      border-radius: 16px;
      padding: 14px;
      display: flex;
      align-items: center;
      gap: 12px;
      margin-bottom: 14px;
      color: #154360;
    }
    .alert-icon { font-size: 1.8rem; }
    .alert-text h4 { font-size: 0.85rem; font-weight: 700; }
    .alert-text p { font-size: 0.75rem; color: #2874A6; margin-top: 2px; }

    .helpline-card {
      background: #FFEBEE;
      border-radius: 16px;
      padding: 14px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 16px;
    }
    .helpline-info h4 { font-size: 0.88rem; color: #B71C1C; font-weight: 800; }
    .helpline-info p { font-size: 0.74rem; color: #C62828; margin-top: 2px; }

    .section-title { font-size: 1.05rem; font-weight: 800; color: var(--text); margin: 18px 0 10px; }

    /* Service Grid */
    .services-grid { display: flex; flex-direction: column; gap: 10px; }
    .service-item {
      background: var(--card-bg);
      border: 1px solid var(--border);
      border-radius: 16px;
      padding: 14px;
      display: flex;
      align-items: center;
      gap: 14px;
      cursor: pointer;
      transition: transform 0.15s, box-shadow 0.15s;
    }
    .service-item:hover { transform: translateY(-2px); box-shadow: var(--shadow); }
    .service-icon-box {
      width: 48px;
      height: 48px;
      border-radius: 14px;
      display: grid;
      place-items: center;
      font-size: 1.5rem;
    }
    .service-info h4 { font-size: 0.95rem; font-weight: 700; color: var(--text); }
    .service-info p { font-size: 0.76rem; color: var(--text-muted); margin-top: 2px; line-height: 1.4; }

    /* Chat view */
    .chat-container { display: flex; flex-direction: column; height: calc(100vh - 170px); }
    .chat-messages { flex: 1; overflow-y: auto; display: flex; flex-direction: column; gap: 10px; padding-bottom: 12px; }
    .bubble {
      max-width: 85%;
      padding: 12px 16px;
      border-radius: 18px;
      font-size: 0.85rem;
      line-height: 1.5;
    }
    .bubble-user {
      align-self: flex-end;
      background: #124D39;
      color: #FFF;
      border-bottom-right-radius: 4px;
    }
    .bubble-bot {
      align-self: flex-start;
      background: var(--primary-light);
      color: var(--text);
      border-bottom-left-radius: 4px;
    }
    .chat-input-row {
      display: flex;
      gap: 8px;
      background: var(--card-bg);
      border: 1px solid var(--border);
      border-radius: 24px;
      padding: 6px 12px;
      align-items: center;
    }
    .chat-input {
      flex: 1;
      border: none;
      background: transparent;
      outline: none;
      font-size: 0.85rem;
      color: var(--text);
    }
    .chips-row { display: flex; gap: 8px; overflow-x: auto; padding-bottom: 8px; margin-bottom: 6px; }
    .chip {
      background: var(--primary-light);
      color: var(--text);
      font-size: 0.72rem;
      font-weight: 600;
      padding: 6px 12px;
      border-radius: 999px;
      border: none;
      cursor: pointer;
      white-space: nowrap;
    }

    /* Forms */
    .form-group { margin-bottom: 12px; }
    .form-group label { display: block; font-size: 0.78rem; font-weight: 700; margin-bottom: 4px; }
    .form-control {
      width: 100%;
      padding: 10px 14px;
      border-radius: 12px;
      border: 1px solid var(--border);
      background: var(--card-bg);
      color: var(--text);
      font-size: 0.85rem;
      outline: none;
    }
    .form-control:focus { border-color: var(--primary); }

    /* Hide unused tabs */
    .tab-view { display: none; }
    .tab-view.active { display: block; }
  </style>
</head>
<body>
  <div class="app-viewport">
    <!-- Top Header -->
    <header class="top-bar">
      <div class="brand-group">
        <div class="brand-emblem">🌾</div>
        <div>
          <div class="brand-title" id="lbl-brand">Kisaan Sathi</div>
          <div class="brand-subtitle" id="lbl-tagline">Smart Farming Support</div>
        </div>
      </div>
      <div class="top-actions">
        <button class="icon-btn" id="lang-btn" title="Toggle English/Hindi">अ/A</button>
        <button class="icon-btn" id="theme-btn" title="Toggle Theme">🌙</button>
        <button class="icon-btn call-btn" id="emergency-call-btn" title="Call Kisan Call Center">📞</button>
      </div>
    </header>

    <!-- Main Screens Content -->
    <main class="screen-content">
      <!-- Android App Install Banner -->
      <div style="background: linear-gradient(135deg, #124D39, #1F6A4F); color: #FFF; padding: 12px 16px; border-radius: 16px; margin-bottom: 14px; display: flex; align-items: center; justify-content: space-between; box-shadow: 0 4px 12px rgba(11,59,42,0.15);">
        <div style="display: flex; align-items: center; gap: 10px;">
          <div style="font-size: 1.6rem;">📱</div>
          <div>
            <div style="font-weight: 800; font-size: 0.85rem;">Install on Android Phone</div>
            <div style="font-size: 0.72rem; color: #FFF0AA;">Native APK (18.6 MB) • Version 1.0</div>
          </div>
        </div>
        <a href="/download-apk" download="kisaan-sathi.apk" style="background: #D9A441; color: #FFF; padding: 8px 14px; border-radius: 999px; text-decoration: none; font-weight: 800; font-size: 0.78rem; display: inline-flex; align-items: center; gap: 4px;">
          📥 Download
        </a>
      </div>
      <!-- 1. Home Tab -->
      <section id="tab-home" class="tab-view active">
        <div class="card hero-card">
          <div class="hero-badge">🚜 Verified Agronomy Advisory</div>
          <h2 class="hero-title" id="h-hero-title">Empowering Every Indian Farmer</h2>
          <p class="hero-desc" id="h-hero-desc">Access scientific crop guides, smart irrigation planners, government schemes, and instant AI farming advisory.</p>
          <button class="btn btn-gold" onclick="switchTab('assistant')">🤖 Ask AI Assistant</button>
        </div>

        <!-- Live OpenWeather & Farm Planning Card -->
        <div class="card" style="background: #E5F3FB; border: 1px solid rgba(25,118,210,0.18); padding: 16px;">
          <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:10px;">
            <div style="display:flex; align-items:center; gap:6px;">
              <span style="font-size:1.1rem;">📍</span>
              <select id="weather-district-select" onchange="updateWeatherDisplay(this.value)" style="border:none; background:rgba(255,255,255,0.8); font-weight:700; font-size:0.8rem; color:#154360; padding:4px 8px; border-radius:8px; outline:none; cursor:pointer;">
                <option value="karnal">Karnal, Haryana</option>
                <option value="ludhiana">Ludhiana, Punjab</option>
                <option value="jaipur">Jaipur, Rajasthan</option>
                <option value="indore">Indore, Madhya Pradesh</option>
                <option value="lucknow">Lucknow, Uttar Pradesh</option>
                <option value="nagpur">Nagpur, Maharashtra</option>
                <option value="patna">Patna, Bihar</option>
                <option value="guntur">Guntur, Andhra Pradesh</option>
              </select>
            </div>
            <button onclick="refreshWeatherPreview()" style="border:none; background:rgba(255,255,255,0.8); border-radius:50%; width:28px; height:28px; cursor:pointer; display:grid; place-items:center;" title="Refresh Weather">🔄</button>
          </div>

          <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:12px;">
            <div>
              <div style="font-size:2rem; font-weight:800; color:#0D47A1; line-height:1;" id="w-temp">26°C</div>
              <div style="font-size:0.75rem; color:#2874A6; margin-top:2px;" id="w-feels">Feels like 27°C • Sunny & Clear</div>
            </div>
            <div id="w-icon" style="font-size:2.4rem;">☀️</div>
          </div>

          <div style="display:grid; grid-template-columns:1fr 1fr 1fr; gap:8px; margin-bottom:10px;">
            <div style="background:rgba(255,255,255,0.7); padding:6px; border-radius:10px; text-align:center;">
              <div style="font-size:0.68rem; color:#2874A6;">💧 Humidity</div>
              <div style="font-size:0.82rem; font-weight:800; color:#0D47A1;" id="w-hum">54%</div>
            </div>
            <div style="background:rgba(255,255,255,0.7); padding:6px; border-radius:10px; text-align:center;">
              <div style="font-size:0.68rem; color:#2874A6;">💨 Wind</div>
              <div style="font-size:0.82rem; font-weight:800; color:#0D47A1;" id="w-wind">11.2 km/h</div>
            </div>
            <div style="background:rgba(255,255,255,0.7); padding:6px; border-radius:10px; text-align:center;">
              <div style="font-size:0.68rem; color:#2874A6;">⏲️ Pressure</div>
              <div style="font-size:0.82rem; font-weight:800; color:#0D47A1;" id="w-press">1013 hPa</div>
            </div>
          </div>

          <!-- Spray Window Banner -->
          <div id="w-spray-box" style="background:#E8F5E9; padding:8px 12px; border-radius:10px; margin-bottom:8px;">
            <div style="font-size:0.78rem; font-weight:800; color:#2E7D32;" id="w-spray-title">🟢 Field Spray Window: Optimal for Spraying</div>
            <div style="font-size:0.72rem; color:#37474F; margin-top:2px;" id="w-spray-desc">Gentle wind (&lt;15 km/h) & clear skies. Optimal conditions for pesticide or foliar nutrient spray.</div>
          </div>

          <!-- Irrigation Advisory -->
          <div style="font-size:0.75rem; color:#154360; line-height:1.4;" id="w-irrigation">
            💧 <strong>Irrigation Advisory:</strong> Favorable soil evaporation conditions. Ideal window for wheat Crown Root Irrigation (CRI).
          </div>
          <!-- Field Work Advisory -->
          <div style="font-size:0.75rem; color:#154360; line-height:1.4; margin-top:6px;" id="w-fieldwork">
            🚜 <strong>Field Work Advisory:</strong> Gentle winds (<12 km/h): Safe and optimal for weeding, foliar spray, and tractor operations.
          </div>
        </div>

        <div class="helpline-card">
          <div class="helpline-info">
            <h4>Kisan Call Center: 1800-180-1551</h4>
            <p>Toll Free • 6:00 AM – 10:00 PM • Native language support</p>
          </div>
          <button class="btn btn-primary" onclick="alert('Dialing Kisan Call Center: 1800-180-1551')">Call</button>
        </div>

        <h3 class="section-title">Core Farming Services</h3>
        <div class="services-grid">
          <div class="service-item" onclick="switchTab('crops')">
            <div class="service-icon-box" style="background:#EBF7EF; color:#124D39;">🌱</div>
            <div class="service-info">
              <h4>Crop Consultation</h4>
              <p>Sowing dates, seed treatments, spacing & rotation advice for wheat, paddy, mustard & cotton.</p>
            </div>
          </div>

          <div class="service-item" onclick="switchTab('irrigation')">
            <div class="service-icon-box" style="background:#E5F3FB; color:#1976D2;">💧</div>
            <div class="service-info">
              <h4>Irrigation Support</h4>
              <p>Water requirement calculator, drip vs sprinkler analysis, and PMKSY subsidy information.</p>
            </div>
          </div>

          <div class="service-item" onclick="switchTab('soil')">
            <div class="service-icon-box" style="background:#FFF5D9; color:#704214;">🛡️</div>
            <div class="service-info">
              <h4>Soil & Pest Care</h4>
              <p>Soil Health Card parameters, organic recipes (NSKE, Chhach), and disease diagnosis.</p>
            </div>
          </div>

          <div class="service-item" onclick="switchTab('schemes')">
            <div class="service-icon-box" style="background:#EDE7F6; color:#512DA8;">🏛️</div>
            <div class="service-info">
              <h4>Government Schemes</h4>
              <p>PM-KISAN ₹6000, PMFBY crop insurance, KCC loan, and application checklists.</p>
            </div>
          </div>
        </div>

        <h3 class="section-title" style="margin-top: 24px;">Group 11 IDT</h3>
        <div style="display:flex; flex-direction:column; gap:10px;">
          <div class="card" style="display:flex; align-items:center; gap:12px; margin-bottom:0;">
            <div style="font-size: 1.8rem; background: var(--primary-light); width: 46px; height: 46px; border-radius: 50%; display: grid; place-items: center;">👨‍💼</div>
            <div>
              <h4 style="font-size:0.92rem; font-weight:800;">Dr. Pushpendra Tyagi</h4>
              <p style="font-size:0.75rem; color:var(--primary); font-weight:700;">Project Coordinator</p>
              <p style="font-size:0.72rem; color:var(--text-muted);">Overall Project Supervision & Coordination</p>
            </div>
          </div>

          <div class="card" style="display:flex; align-items:center; gap:12px; margin-bottom:0;">
            <div style="font-size: 1.8rem; background: #E5F3FB; width: 46px; height: 46px; border-radius: 50%; display: grid; place-items: center;">👨‍💻</div>
            <div>
              <h4 style="font-size:0.92rem; font-weight:800;">Kaushal Dubey</h4>
              <p style="font-size:0.75rem; color:#1976D2; font-weight:700;">Team Lead</p>
              <p style="font-size:0.72rem; color:var(--text-muted);">Technical Direction & Team Leadership</p>
            </div>
          </div>

          <div class="card" style="display:flex; align-items:center; gap:12px; margin-bottom:0;">
            <div style="font-size: 1.8rem; background: #FFF5D9; width: 46px; height: 46px; border-radius: 50%; display: grid; place-items: center;">🤖</div>
            <div>
              <h4 style="font-size:0.92rem; font-weight:800;">Dilshaj Ashrafi</h4>
              <p style="font-size:0.75rem; color:#B88628; font-weight:700;">Full Stack AI Engineer</p>
              <p style="font-size:0.72rem; color:var(--text-muted);">AI Systems & Full Stack Application Engineering</p>
            </div>
          </div>

          <div class="card" style="display:flex; align-items:center; gap:12px; margin-bottom:0;">
            <div style="font-size: 1.8rem; background: #EBF7EF; width: 46px; height: 46px; border-radius: 50%; display: grid; place-items: center;">🌾</div>
            <div>
              <h4 style="font-size:0.92rem; font-weight:800;">Gaurav Raj</h4>
              <p style="font-size:0.75rem; color:var(--primary); font-weight:700;">Agricultural Researcher</p>
              <p style="font-size:0.72rem; color:var(--text-muted);">Agronomic Research & Field Validation</p>
            </div>
          </div>

          <div class="card" style="display:flex; align-items:center; gap:12px; margin-bottom:14px;">
            <div style="font-size: 1.8rem; background: #EDE7F6; width: 46px; height: 46px; border-radius: 50%; display: grid; place-items: center;">📝</div>
            <div>
              <h4 style="font-size:0.92rem; font-weight:800;">Abhishek Kumar Singh</h4>
              <p style="font-size:0.75rem; color:#512DA8; font-weight:700;">Presentation & Documentation Writer</p>
              <p style="font-size:0.72rem; color:var(--text-muted);">Technical Writing & Project Presentation</p>
            </div>
          </div>
        </div>

        <div class="card" style="background:var(--primary-light); border:none; text-align:center;">
          <h4 style="font-weight:800; font-size:0.95rem;">Have a question about your field?</h4>
          <p style="font-size:0.76rem; color:var(--text-muted); margin: 6px 0 14px;">Book a personalized advisory request with our specialist agronomists.</p>
          <button class="btn btn-primary" onclick="switchTab('consult')">Request Consultation</button>
        </div>
      </section>

      <!-- 2. Crops Tab -->
      <section id="tab-crops" class="tab-view">
        <h3 class="section-title">Crop Consultation & Guides</h3>
        <div class="card">
          <div style="display:flex; justify-content:space-between; align-items:center;">
            <h4 style="font-size:1.05rem; font-weight:800; color:var(--primary);">Wheat (Gehun)</h4>
            <span style="background:var(--primary-light); color:var(--primary); font-size:0.7rem; font-weight:700; padding:4px 8px; border-radius:6px;">Rabi Season</span>
          </div>
          <p style="font-size:0.78rem; margin:8px 0; color:var(--text-muted);"><strong>Soil:</strong> Well-drained loamy soil, pH 6.0 - 7.5</p>
          <p style="font-size:0.78rem; margin:4px 0; color:var(--text-muted);"><strong>Sowing:</strong> Nov 1 - Nov 25; Row spacing 20-22 cm</p>
          <p style="font-size:0.78rem; margin:4px 0; color:var(--text-muted);"><strong>Critical Water:</strong> First irrigation at Crown Root (CRI) stage (20-25 days after sowing).</p>
          <p style="font-size:0.78rem; margin:4px 0; color:var(--text-muted);"><strong>Pest Vigilance:</strong> Yellow Rust (Puccinia striiformis), Karnal Bunt.</p>
        </div>

        <div class="card">
          <div style="display:flex; justify-content:space-between; align-items:center;">
            <h4 style="font-size:1.05rem; font-weight:800; color:var(--primary);">Paddy / Rice (Dhan)</h4>
            <span style="background:var(--primary-light); color:var(--primary); font-size:0.7rem; font-weight:700; padding:4px 8px; border-radius:6px;">Kharif Season</span>
          </div>
          <p style="font-size:0.78rem; margin:8px 0; color:var(--text-muted);"><strong>Soil:</strong> Clayey or heavy loamy soil with high water retention.</p>
          <p style="font-size:0.78rem; margin:4px 0; color:var(--text-muted);"><strong>Spacing:</strong> 20 cm x 15 cm with 2-3 seedlings per hill.</p>
          <p style="font-size:0.78rem; margin:4px 0; color:var(--text-muted);"><strong>Water Tip:</strong> Alternate Wetting and Drying (AWD) saves up to 30% water without yield reduction.</p>
        </div>

        <div class="card">
          <div style="display:flex; justify-content:space-between; align-items:center;">
            <h4 style="font-size:1.05rem; font-weight:800; color:var(--primary);">Mustard (Sarson)</h4>
            <span style="background:var(--primary-light); color:var(--primary); font-size:0.7rem; font-weight:700; padding:4px 8px; border-radius:6px;">Rabi Season</span>
          </div>
          <p style="font-size:0.78rem; margin:8px 0; color:var(--text-muted);"><strong>Soil:</strong> Light to medium loam with good drainage.</p>
          <p style="font-size:0.78rem; margin:4px 0; color:var(--text-muted);"><strong>Fertilizer:</strong> Apply sulfur (20-40 kg/ha) to increase seed oil content by 2-3%.</p>
        </div>
      </section>

      <!-- 3. Irrigation Tab -->
      <section id="tab-irrigation" class="tab-view">
        <h3 class="section-title">Smart Irrigation Calculator</h3>
        <div class="card">
          <div class="form-group">
            <label>Crop Type</label>
            <select id="calc-crop" class="form-control">
              <option value="wheat">Wheat (गेहूं)</option>
              <option value="paddy">Paddy / Rice (धान)</option>
              <option value="cotton">Cotton (कपास)</option>
              <option value="mustard">Mustard (सरसों)</option>
              <option value="vegetables">Vegetables (सब्जियां)</option>
            </select>
          </div>
          <div class="form-group">
            <label>Soil Type</label>
            <select id="calc-soil" class="form-control">
              <option value="loamy">Loamy (दोमट)</option>
              <option value="sandy">Sandy (बलुई)</option>
              <option value="clayey">Clayey (चिकनी)</option>
            </select>
          </div>
          <div class="form-group">
            <label>Farm Area (Acres)</label>
            <input type="number" id="calc-area" class="form-control" value="2.5" min="0.1" step="0.5" />
          </div>
          <button class="btn btn-primary btn-full" onclick="computeIrrigation()">Calculate Water Budget</button>

          <div id="calc-result" style="display:none; margin-top:16px; padding:12px; border-radius:12px; background:var(--primary-light);">
            <h4 style="font-size:0.85rem; font-weight:800; color:var(--primary);">Recommended Schedule</h4>
            <p id="res-liters" style="font-size:0.82rem; font-weight:700; margin-top:4px;"></p>
            <p id="res-system" style="font-size:0.75rem; color:var(--text-muted); margin-top:2px;"></p>
            <p style="font-size:0.72rem; color:var(--accent-gold); margin-top:4px;">💡 Micro-irrigation attracts up to 55% PMKSY government subsidy.</p>
          </div>
        </div>

        <h3 class="section-title">Irrigation Systems Comparison</h3>
        <div class="card">
          <h4 style="font-size:0.9rem; font-weight:700;">💧 Drip Irrigation (90-95% Efficiency)</h4>
          <p style="font-size:0.76rem; color:var(--text-muted); margin-top:4px;">Direct water & fertigation to root zone. Saves 50-60% water, prevents weed growth.</p>
        </div>
        <div class="card">
          <h4 style="font-size:0.9rem; font-weight:700;">🌧️ Sprinkler Irrigation (75-85% Efficiency)</h4>
          <p style="font-size:0.76rem; color:var(--text-muted); margin-top:4px;">Rain simulation ideal for wheat, mustard & pulses on undulating surfaces.</p>
        </div>
      </section>

      <!-- 4. Soil & Pest Tab -->
      <section id="tab-soil" class="tab-view">
        <h3 class="section-title">Soil & Integrated Pest Care</h3>
        <div class="card">
          <h4 style="font-size:0.95rem; font-weight:800; color:var(--primary);">Soil Health Card 12 Parameters</h4>
          <p style="font-size:0.76rem; color:var(--text-muted); margin-top:6px; line-height:1.5;">
            <strong>Primary:</strong> N, P, K<br>
            <strong>Secondary:</strong> Sulphur (S)<br>
            <strong>Micronutrients:</strong> Zinc (Zn), Iron (Fe), Copper (Cu), Manganese (Mn), Boron (B)<br>
            <strong>Physical:</strong> pH, Electrical Conductivity (EC), Organic Carbon (OC)
          </p>
        </div>

        <h3 class="section-title">Organic Bio-Remedies</h3>
        <div class="card">
          <h4 style="font-size:0.9rem; font-weight:700;">🌿 Neem Seed Kernel Extract (NSKE 5%)</h4>
          <p style="font-size:0.76rem; color:var(--text-muted); margin-top:4px;">Crush 5kg neem seeds in 100L water. Excellent against aphids, jassids, and early caterpillars.</p>
        </div>
        <div class="card">
          <h4 style="font-size:0.9rem; font-weight:700;">🥛 Fermented Sour Buttermilk (छाछ)</h4>
          <p style="font-size:0.76rem; color:var(--text-muted); margin-top:4px;">Mix 5L sour buttermilk in 100L water. Protects wheat from rust and vegetables from powdery mildew.</p>
        </div>
      </section>

      <!-- 5. Schemes Tab -->
      <section id="tab-schemes" class="tab-view">
        <h3 class="section-title">Government Schemes Directory</h3>
        <div class="form-group">
          <input type="text" id="scheme-search" class="form-control" placeholder="Search scheme (e.g. PM-KISAN, KCC, Bima)..." onkeyup="filterSchemes()" />
        </div>

        <div id="schemes-list">
          <div class="card scheme-card">
            <h4 style="font-size:0.95rem; font-weight:800; color:var(--primary);">PM-KISAN (Pradhan Mantri Kisan Samman Nidhi)</h4>
            <div style="background:var(--primary-light); padding:8px 12px; border-radius:8px; margin:8px 0; font-size:0.8rem; font-weight:700;">
              ₹6,000 / year direct income support in 3 equal installments of ₹2,000 via DBT.
            </div>
            <p style="font-size:0.75rem; color:var(--text-muted);"><strong>Eligibility:</strong> Landholding farmer families with cultivable land; eKYC mandatory.</p>
            <p style="font-size:0.75rem; color:var(--text-muted); margin-top:4px;"><strong>Portal:</strong> pmkisan.gov.in</p>
          </div>

          <div class="card scheme-card">
            <h4 style="font-size:0.95rem; font-weight:800; color:var(--primary);">PMFBY (Crop Insurance Scheme)</h4>
            <div style="background:var(--primary-light); padding:8px 12px; border-radius:8px; margin:8px 0; font-size:0.8rem; font-weight:700;">
              Comprehensive crop failure cover against drought, flood, pests at 1.5% to 2% premium.
            </div>
            <p style="font-size:0.75rem; color:var(--text-muted);"><strong>Report Window:</strong> Localized crop losses must be reported within 72 hours.</p>
            <p style="font-size:0.75rem; color:var(--text-muted); margin-top:4px;"><strong>Portal:</strong> pmfby.gov.in</p>
          </div>

          <div class="card scheme-card">
            <h4 style="font-size:0.95rem; font-weight:800; color:var(--primary);">Kisan Credit Card (KCC)</h4>
            <div style="background:var(--primary-light); padding:8px 12px; border-radius:8px; margin:8px 0; font-size:0.8rem; font-weight:700;">
              Agricultural loan up to ₹3 Lakh at only 4% effective interest rate with prompt repayment.
            </div>
            <p style="font-size:0.75rem; color:var(--text-muted);"><strong>Apply:</strong> Submit simple 1-page form at any cooperative or commercial bank branch.</p>
          </div>
        </div>
      </section>

      <!-- 6. AI Assistant Tab -->
      <section id="tab-assistant" class="tab-view">
        <div class="chat-container">
          <div class="chips-row">
            <button class="chip" onclick="askQuick('When should I do CRI irrigation for wheat?')">Wheat CRI Timing</button>
            <button class="chip" onclick="askQuick('How to register for PM-KISAN ₹6000?')">PM-KISAN ₹6000</button>
            <button class="chip" onclick="askQuick('What is the organic remedy for Yellow Rust in wheat?')">Yellow Rust Remedy</button>
            <button class="chip" onclick="askQuick('How much subsidy on Drip Irrigation under PMKSY?')">Drip Subsidy</button>
          </div>
          <div class="chat-messages" id="chat-msgs">
            <div class="bubble bubble-bot">
              Namaste Kisan Bhai! 🙏 I am your Kisaan Sathi AI Farming Assistant. How can I help you with crops, weather advisories, irrigation planning, soil care, or government schemes today?
            </div>
          </div>
          <div class="chat-input-row">
            <input type="text" id="chat-input" class="chat-input" placeholder="Ask your farming query..." onkeydown="if(event.key==='Enter') sendChat()" />
            <button class="btn btn-primary" style="padding:6px 14px; border-radius:20px;" onclick="sendChat()">Send</button>
          </div>
        </div>
      </section>

      <!-- 7. Consultation Booking Tab -->
      <section id="tab-consult" class="tab-view">
        <h3 class="section-title">Request Agronomist Consultation</h3>
        <div class="card">
          <div class="form-group">
            <label>Farmer Name *</label>
            <input type="text" id="f-name" class="form-control" placeholder="e.g. Ramesh Patel" />
          </div>
          <div class="form-group">
            <label>Mobile Number *</label>
            <input type="tel" id="f-phone" class="form-control" placeholder="e.g. 9876543210" />
          </div>
          <div class="form-group">
            <label>Crop Name</label>
            <input type="text" id="f-crop" class="form-control" placeholder="e.g. Wheat / Mustard" />
          </div>
          <div class="form-group">
            <label>Describe Issue / Symptoms *</label>
            <textarea id="f-desc" class="form-control" rows="3" placeholder="Describe plant discoloration, pest appearance, or growth questions..."></textarea>
          </div>
          <button class="btn btn-primary btn-full" onclick="submitBooking()">Submit Consultation Request</button>
        </div>

        <div id="booking-success" class="card" style="display:none; background:#EBF7EF; border:1px solid #62C587;">
          <h4 style="font-weight:800; color:#124D39;">🎉 Request Received Successfully!</h4>
          <p style="font-size:0.8rem; margin:6px 0;"><strong>Ticket ID:</strong> <span id="res-ticket" style="color:#D9A441; font-weight:700;"></span></p>
          <p style="font-size:0.75rem; color:#5D6D64;">Our agricultural extension officer will contact you within 24 hours.</p>
        </div>
      </section>
    </main>

    <!-- Bottom Navigation -->
    <nav class="bottom-bar">
      <button class="nav-item active" onclick="switchTab('home')">
        <span class="nav-icon">🏠</span>
        <span>Home</span>
      </button>
      <button class="nav-item" onclick="switchTab('crops')">
        <span class="nav-icon">🌱</span>
        <span>Crops</span>
      </button>
      <button class="nav-item" onclick="switchTab('irrigation')">
        <span class="nav-icon">💧</span>
        <span>Water</span>
      </button>
      <button class="nav-item" onclick="switchTab('soil')">
        <span class="nav-icon">🛡️</span>
        <span>Soil/Pest</span>
      </button>
      <button class="nav-item" onclick="switchTab('schemes')">
        <span class="nav-icon">🏛️</span>
        <span>Schemes</span>
      </button>
      <button class="nav-item" onclick="switchTab('assistant')">
        <span class="nav-icon">🤖</span>
        <span>Agri AI</span>
      </button>
    </nav>
  </div>

  <script>
    let isHindi = false;
    let isDark = false;

    function switchTab(tabId) {
      document.querySelectorAll('.tab-view').forEach(t => t.classList.remove('active'));
      const target = document.getElementById('tab-' + tabId);
      if (target) target.classList.add('active');

      const tabs = ['home', 'crops', 'irrigation', 'soil', 'schemes', 'assistant'];
      document.querySelectorAll('.nav-item').forEach((btn, idx) => {
        if (tabs[idx] === tabId) btn.classList.add('active');
        else btn.classList.remove('active');
      });
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    document.getElementById('theme-btn').addEventListener('click', () => {
      isDark = !isDark;
      document.documentElement.dataset.theme = isDark ? 'dark' : 'light';
      document.getElementById('theme-btn').textContent = isDark ? '☀️' : '🌙';
    });

    document.getElementById('lang-btn').addEventListener('click', () => {
      isHindi = !isHindi;
      document.getElementById('lbl-brand').textContent = isHindi ? 'किसान साथी' : 'Kisaan Sathi';
      document.getElementById('lbl-tagline').textContent = isHindi ? 'स्मार्ट खेती, खुशहाल किसान' : 'Smart Farming Support';
      document.getElementById('h-hero-title').textContent = isHindi ? 'हर भारतीय किसान का सच्चा साथी' : 'Empowering Every Indian Farmer';
      document.getElementById('lang-btn').textContent = isHindi ? 'Eng' : 'हिंदी';
    });

    document.getElementById('emergency-call-btn').addEventListener('click', () => {
      alert('Kisan Call Center (Toll Free): 1800-180-1551\\nAvailable 6:00 AM - 10:00 PM in all Indian languages.');
    });

    function computeIrrigation() {
      const crop = document.getElementById('calc-crop').value;
      const soil = document.getElementById('calc-soil').value;
      const area = parseFloat(document.getElementById('calc-area').value) || 1.0;

      let baseWater = 9000;
      let sys = "Sprinkler or Drip";
      if (crop === 'paddy') { baseWater = 22000; sys = "Alternate Wetting and Drying (AWD)"; }
      else if (crop === 'cotton') { baseWater = 11000; sys = "Inline Drip with Fertigation"; }
      else if (crop === 'mustard') { baseWater = 6000; sys = "Sprinkler System"; }

      let soilFactor = 1.0;
      if (soil === 'sandy') soilFactor = 1.35;
      else if (soil === 'clayey') soilFactor = 0.85;

      const dailyLiters = Math.round(baseWater * area * soilFactor);
      document.getElementById('res-liters').textContent = "Daily Requirement: " + dailyLiters.toLocaleString() + " Liters / day";
      document.getElementById('res-system').textContent = "Recommended Method: " + sys;
      document.getElementById('calc-result').style.display = 'block';
    }

    function filterSchemes() {
      const query = document.getElementById('scheme-search').value.toLowerCase();
      document.querySelectorAll('.scheme-card').forEach(card => {
        const text = card.innerText.toLowerCase();
        card.style.display = text.includes(query) ? 'block' : 'none';
      });
    }

    function askQuick(text) {
      document.getElementById('chat-input').value = text;
      sendChat();
    }

    async function sendChat() {
      const input = document.getElementById('chat-input');
      const text = input.value.trim();
      if (!text) return;
      input.value = '';

      const msgs = document.getElementById('chat-msgs');
      const userBubble = document.createElement('div');
      userBubble.className = 'bubble bubble-user';
      userBubble.textContent = text;
      msgs.appendChild(userBubble);
      msgs.scrollTop = msgs.scrollHeight;

      const botBubble = document.createElement('div');
      botBubble.className = 'bubble bubble-bot';
      botBubble.textContent = "Analyzing agricultural guidelines...";
      msgs.appendChild(botBubble);
      msgs.scrollTop = msgs.scrollHeight;

      try {
        const res = await fetch('/api/chat', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ message: text, language: isHindi ? 'hi' : 'en' })
        });
        const data = await res.json();
        botBubble.textContent = data.reply || "For this issue, consult your local Krishi Vigyan Kendra (KVK) or Kisan Call Center at 1800-180-1551.";
      } catch (e) {
        botBubble.textContent = "Kisan Call Center: 1800-180-1551. For personalized advice, consult Dr. Pushpendra Tyagi and agronomist panel.";
      }
      msgs.scrollTop = msgs.scrollHeight;
    }

    function submitBooking() {
      const name = document.getElementById('f-name').value.trim();
      const phone = document.getElementById('f-phone').value.trim();
      const desc = document.getElementById('f-desc').value.trim();
      if (!name || !phone || !desc) {
        alert('Please fill Farmer Name, Phone Number, and Issue Description.');
        return;
      }
      const ticketId = 'KS-' + Math.floor(100000 + Math.random() * 900000);
      document.getElementById('res-ticket').textContent = ticketId;
      document.getElementById('booking-success').style.display = 'block';
      document.getElementById('f-desc').value = '';
    }

    const weatherData = {
      karnal: { temp: '26°C', feels: 'Feels like 27°C • Clear & Sunny', icon: '☀️', hum: '54%', wind: '11.2 km/h', press: '1013 hPa', sprayTitle: '🟢 Field Spray Window: Optimal for Spraying', sprayDesc: 'Gentle wind (<15 km/h) & clear skies. Optimal conditions for pesticide or foliar nutrient spray.', sprayBg: '#E8F5E9', sprayColor: '#2E7D32', irrigation: '💧 <strong>Irrigation Advisory:</strong> Favorable soil evaporation conditions. Ideal window for wheat Crown Root Irrigation (CRI).' },
      ludhiana: { temp: '25°C', feels: 'Feels like 26°C • Clear Sky', icon: '☀️', hum: '52%', wind: '9.8 km/h', press: '1014 hPa', sprayTitle: '🟢 Field Spray Window: Optimal for Spraying', sprayDesc: 'Calm breeze & dry foliage. Great window for weedicide and micronutrient application.', sprayBg: '#E8F5E9', sprayColor: '#2E7D32', irrigation: '💧 <strong>Irrigation Advisory:</strong> Normal evapotranspiration. Maintain scheduled wheat tillering irrigation.' },
      jaipur: { temp: '30°C', feels: 'Feels like 31°C • Warm & Dry', icon: '☀️', hum: '38%', wind: '14.5 km/h', press: '1011 hPa', sprayTitle: '🟡 Field Spray Window: Spray with Caution', sprayDesc: 'High midday temperature. Spray only during early morning (6-9 AM) to avoid rapid drying.', sprayBg: '#FFF8E1', sprayColor: '#F57F17', irrigation: '💧 <strong>Irrigation Advisory:</strong> High evaporation rate. Schedule watering in early morning or evening.' },
      indore: { temp: '28°C', feels: 'Feels like 29°C • Pleasant', icon: '🌤️', hum: '48%', wind: '12.0 km/h', press: '1012 hPa', sprayTitle: '🟢 Field Spray Window: Optimal for Spraying', sprayDesc: 'Pleasant temperature and low wind. Suitable for foliar sprays.', sprayBg: '#E8F5E9', sprayColor: '#2E7D32', irrigation: '💧 <strong>Irrigation Advisory:</strong> Soil moisture adequate. Irrigate light soils.' },
      lucknow: { temp: '27°C', feels: 'Feels like 28°C • Hazy Sunshine', icon: '🌤️', hum: '58%', wind: '10.5 km/h', press: '1013 hPa', sprayTitle: '🟢 Field Spray Window: Optimal for Spraying', sprayDesc: 'Stable atmosphere and light wind. Good window for field operations.', sprayBg: '#E8F5E9', sprayColor: '#2E7D32', irrigation: '💧 <strong>Irrigation Advisory:</strong> Moderate water demand. Ideal for mustard pod-filling irrigation.' },
      nagpur: { temp: '29°C', feels: 'Feels like 30°C • Sunny', icon: '☀️', hum: '44%', wind: '13.2 km/h', press: '1012 hPa', sprayTitle: '🟢 Field Spray Window: Optimal for Spraying', sprayDesc: 'Safe spray conditions. Check wind direction before spraying cotton.', sprayBg: '#E8F5E9', sprayColor: '#2E7D32', irrigation: '💧 <strong>Irrigation Advisory:</strong> Drip irrigation recommended to conserve water.' },
      patna: { temp: '27°C', feels: 'Feels like 29°C • Partly Cloudy', icon: '⛅', hum: '76%', wind: '16.5 km/h', press: '1010 hPa', sprayTitle: '🟡 Field Spray Window: Spray with Caution', sprayDesc: 'Higher humidity and moderate wind. Ensure foliage is completely dry before spraying.', sprayBg: '#FFF8E1', sprayColor: '#F57F17', irrigation: '💧 <strong>Irrigation Advisory:</strong> Monitor rain forecast; delay irrigation if cloud buildup intensifies.' },
      guntur: { temp: '31°C', feels: 'Feels like 34°C • Humid', icon: '⛅', hum: '82%', wind: '18.0 km/h', press: '1009 hPa', sprayTitle: '🟡 Field Spray Window: Spray with Caution', sprayDesc: 'High humidity and gusty wind. Prefer morning hours for any essential treatment.', sprayBg: '#FFF8E1', sprayColor: '#F57F17', irrigation: '💧 <strong>Irrigation Advisory:</strong> Frequent light irrigations advised for horticultural crops.' }
    };

    function updateWeatherDisplay(districtKey) {
      const data = weatherData[districtKey] || weatherData.karnal;
      document.getElementById('w-temp').textContent = data.temp;
      document.getElementById('w-feels').textContent = data.feels;
      document.getElementById('w-icon').textContent = data.icon;
      document.getElementById('w-hum').textContent = data.hum;
      document.getElementById('w-wind').textContent = data.wind;
      document.getElementById('w-press').textContent = data.press;
      const box = document.getElementById('w-spray-box');
      box.style.background = data.sprayBg;
      const title = document.getElementById('w-spray-title');
      title.textContent = data.sprayTitle;
      title.style.color = data.sprayColor;
      document.getElementById('w-spray-desc').textContent = data.sprayDesc;
      document.getElementById('w-irrigation').innerHTML = data.irrigation;
      const fw = document.getElementById('w-fieldwork');
      if (fw) {
        fw.innerHTML = data.fieldwork || '🚜 <strong>Field Work Advisory:</strong> Gentle winds: Safe for weeding, intercultural operations, and foliar spray.';
      }
    }

    async function refreshWeatherPreview() {
      const select = document.getElementById('weather-district-select');
      const district = select.value;
      try {
        const res = await fetch('/api/weather?q=' + encodeURIComponent(district));
        if (res.ok) {
          const live = await res.json();
          if (live && live.main) {
            document.getElementById('w-temp').textContent = Math.round(live.main.temp) + '°C';
            document.getElementById('w-feels').textContent = 'Feels like ' + Math.round(live.main.feels_like) + '°C • ' + (live.weather[0]?.description || 'Live');
            document.getElementById('w-hum').textContent = live.main.humidity + '%';
            document.getElementById('w-press').textContent = live.main.pressure + ' hPa';
            document.getElementById('w-wind').textContent = ((live.wind?.speed || 0) * 3.6).toFixed(1) + ' km/h';
            return;
          }
        }
      } catch (e) {}
      updateWeatherDisplay(district);
    }
  </script>
</body>
</html>`;

const server = createServer(async (req, res) => {
  const host = req.headers.host || 'localhost';
  const url = new URL(req.url || '/', 'http://' + host);

  if (url.pathname === '/api/chat' && req.method === 'POST') {
    let body = '';
    req.on('data', chunk => body += chunk);
    req.on('end', async () => {
      try {
        const parsed = JSON.parse(body || '{}');
        const q = (parsed.message || '').toLowerCase();
        let reply = '';

        if (q.includes('pm-kisan') || q.includes('6000') || q.includes('सम्मान निधि')) {
          reply = "Under PM-KISAN, eligible farmers receive ₹6,000 per year directly in their bank accounts via DBT in 3 installments of ₹2,000. Ensure your Aadhaar is linked and eKYC is completed on pmkisan.gov.in.";
        } else if (q.includes('water') || q.includes('irrigation') || q.includes('सिंचाई') || q.includes('drip') || q.includes('cri')) {
          reply = "For wheat, the Crown Root Initiation (CRI) stage at 20-25 days after sowing is critical for first watering. Adopting drip or sprinkler systems saves 40-50% water and qualifies for up to 55% subsidy under PMKSY.";
        } else if (q.includes('rust') || q.includes('pest') || q.includes('कीट') || q.includes('रोग')) {
          reply = "For early pests, apply 5% Neem Seed Kernel Extract (NSKE) or 5L fermented sour buttermilk in 100L water. For severe Yellow Rust or Blast disease, promptly contact your local Krishi Vigyan Kendra (KVK).";
        } else if (q.includes('helpline') || q.includes('call') || q.includes('phone') || q.includes('नंबर')) {
          reply = "You can call the Government of India Kisan Call Center Toll-Free: 1800-180-1551 (6:00 AM to 10:00 PM, 7 days a week) in your native language.";
        } else {
          reply = "Thank you for asking! For optimal guidance on " + (parsed.message || 'your crop') + ", provide your soil type and sowing date, or submit a request directly to our agronomist panel in the consultation tab.";
        }

        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ reply }));
      } catch (err) {
        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ reply: "Kisan Call Center: 1800-180-1551." }));
      }
    });
    return;
  }

  if (url.pathname === '/api/weather') {
    const q = url.searchParams.get('q') || 'Karnal';
    const apiKey = process.env.OPENWEATHER_API_KEY || '';
    if (apiKey) {
      try {
        const fetchRes = await fetch(`https://api.openweathermap.org/data/2.5/weather?q=${encodeURIComponent(q)},IN&appid=${apiKey}&units=metric`);
        if (fetchRes.ok) {
          const data = await fetchRes.json();
          res.writeHead(200, { 'Content-Type': 'application/json' });
          res.end(JSON.stringify(data));
          return;
        }
      } catch (err) {}
    }

    // Free keyless Open-Meteo fallback
    try {
      const geo = await fetch(`https://geocoding-api.open-meteo.com/v1/search?name=${encodeURIComponent(q)}&count=1&language=en&format=json`);
      if (geo.ok) {
        const geoData = await geo.json();
        if (geoData.results && geoData.results[0]) {
          const { latitude, longitude } = geoData.results[0];
          const w = await fetch(`https://api.open-meteo.com/v1/forecast?latitude=${latitude}&longitude=${longitude}&current=temperature_2m,relative_humidity_2m,apparent_temperature,surface_pressure,wind_speed_10m`);
          if (w.ok) {
            const wData = await w.json();
            const cur = wData.current || {};
            res.writeHead(200, { 'Content-Type': 'application/json' });
            res.end(JSON.stringify({
              main: {
                temp: cur.temperature_2m || 26,
                feels_like: cur.apparent_temperature || 27,
                humidity: cur.relative_humidity_2m || 50,
                pressure: Math.round(cur.surface_pressure || 1013)
              },
              wind: { speed: ((cur.wind_speed_10m || 10) / 3.6) },
              weather: [{ main: 'Clear', description: 'Real-time telemetry' }]
            }));
            return;
          }
        }
      }
    } catch (e) {}

    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({
      main: { temp: 26, feels_like: 27, humidity: 54, pressure: 1013 },
      wind: { speed: 3.1 },
      weather: [{ main: 'Clear', description: 'Clear sky' }]
    }));
    return;
  }

  if (url.pathname === '/download-apk' || url.pathname === '/app-debug.apk') {
    const apkPath = join(process.cwd(), 'app/build/outputs/apk/debug/app-debug.apk');
    if (existsSync(apkPath)) {
      const stat = readFileSync(apkPath);
      res.writeHead(200, {
        'Content-Type': 'application/vnd.android.package-archive',
        'Content-Disposition': 'attachment; filename="kisaan-sathi.apk"',
        'Content-Length': stat.length
      });
      res.end(stat);
      return;
    }
  }

  res.writeHead(200, {
    'Content-Type': 'text/html; charset=utf-8',
    'Cache-Control': 'no-cache'
  });
  res.end(htmlContent);
});

server.listen(port, '0.0.0.0', () => {
  console.log('Kisaan Sathi is running on http://0.0.0.0:' + port);
});

