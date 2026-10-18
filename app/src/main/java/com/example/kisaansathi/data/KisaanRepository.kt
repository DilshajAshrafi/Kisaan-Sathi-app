package com.example.kisaansathi.data

import com.example.kisaansathi.model.AgriExpert
import com.example.kisaansathi.model.CropGuide
import com.example.kisaansathi.model.IrrigationAdvice
import com.example.kisaansathi.model.Language
import com.example.kisaansathi.model.PestCareItem
import com.example.kisaansathi.model.SchemeInfo

object KisaanRepository {

    val crops = listOf(
        CropGuide(
            id = "wheat",
            nameEn = "Wheat (Gehun)",
            nameHi = "गेहूं (Wheat)",
            seasonEn = "Rabi (Winter)",
            seasonHi = "रबी (सर्दियों का मौसम)",
            soilTypeEn = "Well-drained loamy soil, pH 6.0 - 7.5",
            soilTypeHi = "अच्छी जल निकासी वाली दोमट मिट्टी, pH 6.0 - 7.5",
            waterNeedEn = "Moderate (4-6 irrigations at critical stages)",
            waterNeedHi = "मध्यम (प्रमुख चरणों में 4-6 सिंचाइयां)",
            sowingPeriodEn = "November 1 - November 25 (Timely sown)",
            sowingPeriodHi = "1 नवंबर से 25 नवंबर (समय पर बुवाई)",
            spacingAdviceEn = "Row to row: 20-22.5 cm; Depth: 4-5 cm",
            spacingAdviceHi = "पंक्ति से पंक्ति: 20-22.5 सेमी; गहराई: 4-5 सेमी",
            keyTipsEn = listOf(
                "First irrigation is critical at CRI stage (20-25 days after sowing).",
                "Apply balanced NPK in ratio 120:60:40 kg/ha based on soil test.",
                "Ensure weed control before 35 days using recommended post-emergence herbicide."
            ),
            keyTipsHi = listOf(
                "पहली सिंचाई ताज जड़ निकलने (CRI) के समय (20-25 दिन बाद) बहुत जरूरी है।",
                "मिट्टी परीक्षण के अनुसार 120:60:40 किग्रा/हेक्टेयर संतुलित NPK दें।",
                "35 दिनों से पहले खरपतवार नियंत्रण अवश्य पूरा करें।"
            ),
            commonDiseasesEn = listOf("Yellow Rust (Puccinia striiformis)", "Karnal Bunt", "Loose Smut"),
            commonDiseasesHi = listOf("पीला रतुआ (Yellow Rust)", "करनाल बंट", "कंडुआ रोग")
        ),
        CropGuide(
            id = "rice",
            nameEn = "Paddy / Rice (Dhan)",
            nameHi = "धान / चावल (Paddy)",
            seasonEn = "Kharif (Monsoon)",
            seasonHi = "खरीफ (मानसून मौसम)",
            soilTypeEn = "Clayey or heavy loamy soil with high water retention",
            soilTypeHi = "चिकनी या भारी दोमट मिट्टी जो जल रोक सके",
            waterNeedEn = "High (Standing water 2-5 cm during tillering)",
            waterNeedHi = "अधिक (कल्ले फूटने के समय 2-5 सेमी पानी)",
            sowingPeriodEn = "Nursery: May-June; Transplanting: June-July",
            sowingPeriodHi = "नर्सरी: मई-जून; रोपाई: जून-जुलाई",
            spacingAdviceEn = "20 cm x 15 cm spacing with 2-3 seedlings per hill",
            spacingAdviceHi = "20 सेमी x 15 सेमी की दूरी, प्रति स्थान 2-3 पौधे",
            keyTipsEn = listOf(
                "Adopt Alternate Wetting and Drying (AWD) to save up to 30% water.",
                "Green manuring with Dhaincha adds 25 kg organic Nitrogen per hectare.",
                "Avoid excessive urea to prevent leaf blast and bacterial leaf blight."
            ),
            keyTipsHi = listOf(
                "वैकल्पिक गीला और सूखा (AWD) तकनीक से 30% पानी बचाएं।",
                "ढैंचा की हरी खाद से प्रति हेक्टेयर 25 किलो जैविक नाइट्रोजन मिलता है।",
                "ब्लास्ट रोग से बचाव के लिए जरूरत से ज्यादा यूरिया न डालें।"
            ),
            commonDiseasesEn = listOf("Paddy Blast", "Bacterial Leaf Blight", "Brown Planthopper"),
            commonDiseasesHi = listOf("धान का झुलसा (Blast)", "जीवाणु पत्ती अंगमारी", "भूरा फुदका")
        ),
        CropGuide(
            id = "mustard",
            nameEn = "Mustard (Sarson)",
            nameHi = "सरसों (Mustard)",
            seasonEn = "Rabi (Winter)",
            seasonHi = "रबी (सर्दियों का मौसम)",
            soilTypeEn = "Light to medium loam with good drainage",
            soilTypeHi = "हल्की से मध्यम दोmट, अच्छी जल निकासी वाली",
            waterNeedEn = "Low to Medium (2-3 irrigations: Flowering & Pod formation)",
            waterNeedHi = "कम से मध्यम (फूल आते समय व फली बनते समय 2 सिंचाई)",
            sowingPeriodEn = "October 1 - October 20",
            sowingPeriodHi = "1 अक्टूबर से 20 अक्टूबर",
            spacingAdviceEn = "Row to row: 30-45 cm; Plant to plant: 10-15 cm",
            spacingAdviceHi = "पंक्ति से पंक्ति: 30-45 सेमी; पौधे से पौधा: 10-15 सेमी",
            keyTipsEn = listOf(
                "Thinning at 15-20 days is essential for strong branching.",
                "Sulfur application (20-40 kg/ha) increases oil content by 2-3%.",
                "Watch out for Aphids (Mahu) during cloudy weather in January."
            ),
            keyTipsHi = listOf(
                "बुवाई के 15-20 दिन बाद छंटाई (Thinning) मजबूत शाखाओं के लिए जरूरी है।",
                "सल्फर (20-40 किग्रा/हेक्टेयर) डालने से तेल की मात्रा 2-3% बढ़ती है।",
                "जनवरी में बादलों वाले मौसम में माहू (Aphids) कीट से सतर्क रहें।"
            ),
            commonDiseasesEn = listOf("White Rust", "Alternaria Blight", "Mustard Aphid"),
            commonDiseasesHi = listOf("सफेद रतुआ (White Rust)", "अल्टरनेरिया ब्लाइट", "माहू (चेपा)")
        ),
        CropGuide(
            id = "cotton",
            nameEn = "Cotton (Kapas)",
            nameHi = "कपास (Cotton)",
            seasonEn = "Kharif",
            seasonHi = "खरीफ",
            soilTypeEn = "Deep black soil (Regur) or alluvial soil with pH 7.0-8.5",
            soilTypeHi = "गहरी काली मिट्टी (रेगुर) या दोमट मिट्टी",
            waterNeedEn = "Medium (Drip irrigation highly beneficial)",
            waterNeedHi = "मध्यम (ड्रिप सिंचाई से सर्वोत्तम पैदावार)",
            sowingPeriodEn = "April 15 - May 15 (North), June-July (Central/South)",
            sowingPeriodHi = "15 अप्रैल से 15 मई (उत्तर), जून-जुलाई (मध्य/दक्षिण)",
            spacingAdviceEn = "67.5 x 60 cm for Bt Cotton hybrids",
            spacingAdviceHi = "बी.टी. कपास के लिए 67.5 x 60 सेमी",
            keyTipsEn = listOf(
                "Install pheromone traps (5 traps/acre) to monitor Pink Bollworm.",
                "Avoid waterlogging at flowering and boll development stage.",
                "Foliar spray of Potassium Nitrate (13:0:45) prevents square dropping."
            ),
            keyTipsHi = listOf(
                "गुलाबी सुंडी की निगरानी के लिए फेरोमोन ट्रैप (5 प्रति एकड़) लगाएं।",
                "फूल और गूलर बनते समय खेत में पानी जमा न होने दें।",
                "पोटेशियम नाइट्रेट (13:0:45) का छिड़काव कलियां झड़ने से रोकता है।"
            ),
            commonDiseasesEn = listOf("Pink Bollworm", "Whitefly", "Cotton Leaf Curl Virus"),
            commonDiseasesHi = listOf("गुलाबी सुंडी (Pink Bollworm)", "सफेद मक्खी", "पत्ता मरोड़ रोग")
        ),
        CropGuide(
            id = "pulses",
            nameEn = "Chickpea / Gram (Chana)",
            nameHi = "चना / दालें (Chickpea)",
            seasonEn = "Rabi",
            seasonHi = "रबी",
            soilTypeEn = "Sandy loam to clay loam, avoids saline soils",
            soilTypeHi = "बलुई दोमट से चिकनी दोमट",
            waterNeedEn = "Low (1-2 protective irrigations)",
            waterNeedHi = "कम (1-2 सुरक्षात्मक सिंचाई)",
            sowingPeriodEn = "October 15 - November 10",
            sowingPeriodHi = "15 अक्टूबर से 10 नवंबर",
            spacingAdviceEn = "30 cm x 10 cm; Sowing depth: 8-10 cm to avoid wilt",
            spacingAdviceHi = "30 x 10 सेमी; उकठा रोग से बचाव हेतु 8-10 सेमी गहरी बुवाई",
            keyTipsEn = listOf(
                "Seed treatment with Rhizobium and Trichoderma is highly recommended.",
                "Do not irrigate during peak flowering as it causes flower drop.",
                "Nipping (pinching apex buds) at 30-40 days increases pod-bearing branches."
            ),
            keyTipsHi = listOf(
                "राइजोबियम और ट्राइकोडर्मा से बीज उपचार अवश्य करें।",
                "पूर्ण फूल आने पर कभी सिंचाई न करें, इससे फूल झड़ जाते हैं।",
                "30-40 दिन बाद पौधों की ऊपरी कलियां तोड़ने (Nipping) से फलियां बढ़ती हैं।"
            ),
            commonDiseasesEn = listOf("Fusarium Wilt", "Pod Borer (Helicoverpa)", "Ascochyta Blight"),
            commonDiseasesHi = listOf("उकठा रोग (Wilt)", "फली छेदक (Pod Borer)", "झुलसा")
        )
    )

    val schemes = listOf(
        SchemeInfo(
            id = "pm_kisan",
            titleEn = "PM-KISAN (Pradhan Mantri Kisan Samman Nidhi)",
            titleHi = "प्रधानमंत्री किसान सम्मान निधि (PM-KISAN)",
            ministryEn = "Ministry of Agriculture & Farmers Welfare, Govt of India",
            ministryHi = "कृषि एवं किसान कल्याण मंत्रालय, भारत सरकार",
            benefitEn = "Direct Income Support of ₹6,000 per year paid in 3 equal installments of ₹2,000 directly into the farmer's bank account via DBT.",
            benefitHi = "₹6,000 प्रति वर्ष की प्रत्यक्ष आय सहायता, 3 किस्तों (₹2,000 प्रत्येक) में सीधे बैंक खाते में DBT के माध्यम से।",
            eligibilityEn = listOf(
                "All landholding farmer families having cultivable land in their names.",
                "Excludes institutional landholders and families holding constitutional posts or high income taxes.",
                "Aadhaar linking and eKYC are mandatory."
            ),
            eligibilityHi = listOf(
                "सभी भूमिधारक किसान परिवार जिनके नाम कृषि योग्य भूमि है।",
                "संवैधानिक पदधारक और आयकर दाता परिवार इसके पात्र नहीं हैं।",
                "आधार लिंक और ई-केवाईसी अनिवार्य है।"
            ),
            requiredDocsEn = listOf("Aadhaar Card", "Land ownership papers (Khasra/Khatauni)", "Bank Passbook with IFSC", "Active mobile number"),
            requiredDocsHi = listOf("आधार कार्ड", "जमीन की खतौनी / जमाबंदी", "बैंक पासबुक", "मोबाइल नंबर"),
            howToApplyEn = "Visit the official PM-KISAN portal (pmkisan.gov.in) -> Click 'New Farmer Registration' -> Enter Aadhaar and land details -> Verify OTP.",
            howToApplyHi = "pmkisan.gov.in पर जाएं -> 'New Farmer Registration' पर क्लिक करें -> आधार और जमीन का विवरण दर्ज करें -> OTP सत्यापित करें।",
            portalUrl = "https://pmkisan.gov.in"
        ),
        SchemeInfo(
            id = "pmfby",
            titleEn = "PMFBY (Pradhan Mantri Fasal Bima Yojana)",
            titleHi = "प्रधानमंत्री फसल बीमा योजना (PMFBY)",
            ministryEn = "Ministry of Agriculture & Farmers Welfare",
            ministryHi = "कृषि एवं किसान कल्याण मंत्रालय",
            benefitEn = "Comprehensive crop insurance cover against non-preventable natural risks (drought, flood, unseasonal hail, pests). Extremely low farmer premium: 2% for Kharif, 1.5% for Rabi, 5% for commercial/horticultural crops.",
            benefitHi = "प्राकृतिक आपदाओं (सूखा, बाढ़, ओलावृष्टि, कीट) से फसल नुकसान पर व्यापक सुरक्षा। खरीफ के लिए मात्र 2% और रबी के लिए 1.5% प्रीमियम।",
            eligibilityEn = listOf(
                "All farmers including sharecroppers and tenant farmers growing notified crops in notified areas.",
                "Available for both loanee (KCC holders) and non-loanee farmers."
            ),
            eligibilityHi = listOf(
                "अधिसूचित क्षेत्र में अधिसूचित फसल उगाने वाले सभी किसान (बटाईदार भी शामिल)।",
                "ऋणी और गैर-ऋणी दोनों प्रकार के किसानों के लिए उपलब्ध।"
            ),
            requiredDocsEn = listOf("Land Record certificate (LPC/RoR)", "Sowing certificate/declaration", "Bank details", "Aadhaar"),
            requiredDocsHi = listOf("जमीन का भू-अभिलेख / खतौनी", "बुवाई प्रमाण पत्र", "बैंक खाता विवरण", "आधार कार्ड"),
            howToApplyEn = "Apply via bank branch, CSC center, or online on pmfby.gov.in within cut-off date. In case of localized crop damage, report within 72 hours via Crop Insurance App.",
            howToApplyHi = "कट-ऑफ तारीख से पहले बैंक, CSC केंद्र या pmfby.gov.in पर आवेदन करें। नुकसान की स्थिति में 72 घंटे में सूचना दें।",
            portalUrl = "https://pmfby.gov.in"
        ),
        SchemeInfo(
            id = "kcc",
            titleEn = "Kisan Credit Card (KCC) Scheme",
            titleHi = "किसान क्रेडिट कार्ड (KCC) योजना",
            ministryEn = "Department of Financial Services / Ministry of Agriculture",
            ministryHi = "वित्तीय सेवा विभाग / कृषि मंत्रालय",
            benefitEn = "Short-term agricultural crop loans up to ₹3 Lakh at an effective interest rate of only 4% (7% base minus 3% prompt repayment incentive). Covers crops, animal husbandry, and fisheries.",
            benefitHi = "₹3 लाख तक का फसली ऋण केवल 4% प्रभावी ब्याज दर पर (समय पर भुगतान करने पर 3% छूट)। पशुपालन और मत्स्य पालन भी शामिल।",
            eligibilityEn = listOf(
                "Individual or joint owner-cultivators.",
                "Tenant farmers, oral lessees, and sharecroppers.",
                "Self Help Groups (SHGs) and Joint Liability Groups (JLGs) of farmers."
            ),
            eligibilityHi = listOf(
                "व्यक्तिगत या संयुक्त किसान।",
                "बटाईदार, किराएदार और पट्टाधारक किसान।",
                "किसानों के स्वयं सहायता समूह (SHG) व संयुक्त देयता समूह (JLG)।"
            ),
            requiredDocsEn = listOf("Application form", "Identity & Address proof (Aadhaar/Voter ID)", "Land cultivation documents", "Passport photo"),
            requiredDocsHi = listOf("आवेदन पत्र", "पहचान व पते का प्रमाण (आधार)", "भूमि दस्तावेज", "पासपोर्ट फोटो"),
            howToApplyEn = "Fill simplified one-page KCC form at your local bank branch, cooperative bank, or nearest CSC.",
            howToApplyHi = "अपनी नजदीकी बैंक शाखा या सहकारी बैंक में एक पेज का सरल KCC फॉर्म भरकर जमा करें।",
            portalUrl = "https://myscheme.gov.in"
        ),
        SchemeInfo(
            id = "pmksy",
            titleEn = "PMKSY - Per Drop More Crop (Micro Irrigation)",
            titleHi = "पीएमकेएसवाई - प्रति बूंद अधिक फसल (सूक्ष्म सिंचाई)",
            ministryEn = "Department of Agriculture & Farmers Welfare",
            ministryHi = "कृषि एवं किसान कल्याण विभाग",
            benefitEn = "Up to 55% subsidy for Small & Marginal farmers and 45% for other farmers for installing Drip and Sprinkler irrigation systems, reducing water usage by 40-50%.",
            benefitHi = "ड्रिप और स्प्रिंकलर सिंचाई सिस्टम लगाने पर छोटे व सीमांत किसानों को 55% और अन्य किसानों को 45% तक सब्सिडी।",
            eligibilityEn = listOf(
                "Farmers who own agricultural land with an assured water source (borewell, open well, pond).",
                "Beneficiaries must not have availed subsidy for the same field in the past 7 years."
            ),
            eligibilityHi = listOf(
                "वे किसान जिनके पास सिंचाई का सुनिश्चित स्रोत (नलकूप, कुआं, तालाब) उपलब्ध हो।",
                "पिछले 7 वर्षों में उस जमीन पर माइक्रो सिंचाई सब्सिडी न ली हो।"
            ),
            requiredDocsEn = listOf("Land registry/7-12 extract", "Electricity bill or water source proof", "Bank passbook", "Aadhaar Card"),
            requiredDocsHi = listOf("जमीन की खतौनी / फर्द", "जल स्रोत / बिजली बिल प्रमाण", "बैंक पासबुक", "आधार"),
            howToApplyEn = "Apply on state horticulture/agriculture portal (e.g. DBT Agriculture) or visit District Agriculture/Horticulture Officer.",
            howToApplyHi = "राज्य के कृषि/उद्यान विभाग पोर्टल पर ऑनलाइन आवेदन करें अथवा जिला कृषि अधिकारी कार्यालय से संपर्क करें।",
            portalUrl = "https://pmksy.gov.in"
        ),
        SchemeInfo(
            id = "soil_health_card",
            titleEn = "Soil Health Card Scheme",
            titleHi = "मृदा स्वास्थ्य कार्ड योजना (Soil Health Card)",
            ministryEn = "Ministry of Agriculture & Farmers Welfare",
            ministryHi = "कृषि एवं किसान कल्याण मंत्रालय",
            benefitEn = "Free scientific testing of soil samples covering 12 vital parameters (NPK, Secondary nutrients S, Micro-nutrients Zn, Fe, Cu, Mn, Bo, and Physical parameters pH, EC, OC) with tailored fertilizer dose advice.",
            benefitHi = "खेत की मिट्टी के 12 मुख्य मापदंडों की निःशुल्क जांच और फसल अनुसार खाद-उर्वरक की सही मात्रा का मार्गदर्शन।",
            eligibilityEn = listOf("Every farmer in India is entitled to a Soil Health Card issued every 3 years for their farm parcel."),
            eligibilityHi = listOf("देश का प्रत्येक किसान अपने खेत के लिए हर 3 साल में मृदा स्वास्थ्य कार्ड पाने का पात्र है।"),
            requiredDocsEn = listOf("Soil sample collected as per sampling guidelines", "Survey/Khasra number", "Farmer identification"),
            requiredDocsHi = listOf("दिशा-निर्देशानुसार लिया गया मिट्टी का नमूना", "खसरा नंबर", "किसान पहचान पत्र"),
            howToApplyEn = "Contact the Gram Panchayat Village Agriculture Assistant or local Krishi Vigyan Kendra (KVK). Soil sample collection is coordinated by field officers.",
            howToApplyHi = "ग्राम पंचायत के कृषि सहायक या स्थानीय कृषि विज्ञान केंद्र (KVK) से संपर्क करें।",
            portalUrl = "https://soilhealth.dac.gov.in"
        )
    )

    val experts = listOf(
        AgriExpert(
            id = "pushpendra",
            name = "Dr. Pushpendra Tyagi",
            designation = "Project Coordinator",
            specialization = "Project Supervision & Agronomy Coordination",
            experience = "Senior Project Coordinator",
            imageRes = null
        ),
        AgriExpert(
            id = "kaushal",
            name = "Kaushal Dubey",
            designation = "Team Lead",
            specialization = "Project Leadership & System Architecture",
            experience = "Team Lead",
            imageRes = null
        ),
        AgriExpert(
            id = "dilshaj",
            name = "Dilshaj Ashrafi",
            designation = "Full Stack AI Engineer",
            specialization = "AI Systems, Full Stack Architecture & Model Engineering",
            experience = "Full Stack AI Engineer",
            imageRes = null
        ),
        AgriExpert(
            id = "gaurav",
            name = "Gaurav Raj",
            designation = "Agricultural Researcher",
            specialization = "Agricultural Research & Field Practices",
            experience = "Agricultural Researcher",
            imageRes = null
        ),
        AgriExpert(
            id = "abhishek",
            name = "Abhishek Kumar Singh",
            designation = "Presentation & Documentation Writer",
            specialization = "Technical Documentation & Project Presentation",
            experience = "Documentation Specialist",
            imageRes = null
        )
    )


    val pestCareItems = listOf(
        PestCareItem(
            id = "yellow_rust",
            cropEn = "Wheat",
            cropHi = "गेहूं",
            pestOrDiseaseEn = "Yellow Rust (Pila Ratua)",
            pestOrDiseaseHi = "पीला रतुआ",
            symptomsEn = "Yellow powdery pustules arranged in linear stripes on leaves, easily rubs off on fingers.",
            symptomsHi = "पत्तियों पर पीले रंग की धारियां और पाउडर जैसी फफूंद, हाथ लगाने पर उंगलियों पर पीला रंग लग जाता है।",
            organicRemedyEn = "Spray fermented butter milk (Chhach) 5L in 100L water per acre as preventive measure.",
            organicRemedyHi = "रोकथाम के लिए 5 लीटर खट्टी छाछ को 100 लीटर पानी में मिलाकर प्रति एकड़ छिड़कें।",
            precautionEn = "Inspect northern & shaded field edges during cold humid mornings in Dec-Jan. Consult local KVK.",
            precautionHi = "दिसंबर-जनवरी में सुबह के समय खेत के कोनों की नियमित जांच करें। कृषि विशेषज्ञ से तुरंत संपर्क करें।"
        ),
        PestCareItem(
            id = "pink_bollworm",
            cropEn = "Cotton",
            cropHi = "कपास",
            pestOrDiseaseEn = "Pink Bollworm (Gulabi Sundi)",
            pestOrDiseaseHi = "गुलाबी सुंडी",
            symptomsEn = "Rosetted flowers, bore holes in developing bolls plugged with excreta, stained lint.",
            symptomsHi = "फूलों का गुलाब की तरह बंद होना, गूलर में छेद और मल भरा होना, रूई खराब होना।",
            organicRemedyEn = "Install 5-8 Pheromone traps/acre. Spray 5% Neem Seed Kernel Extract (NSKE) or Neem Oil (1500 ppm).",
            organicRemedyHi = "प्रति एकड़ 5-8 फेरोमोन ट्रैप लगाएं। 5% नीम गिरी का काढ़ा (NSKE) या नीम तेल (1500 ppm) छिड़कें।",
            precautionEn = "Collect and destroy dropped rosette flowers and infested bolls. Avoid extending cotton season beyond 150 days.",
            precautionHi = "गिरे हुए प्रभावित फूलों व गूलरों को इकट्ठा कर नष्ट करें। कपास की फसल 150 दिनों से अधिक न खीचें।"
        ),
        PestCareItem(
            id = "paddy_blast",
            cropEn = "Paddy / Rice",
            cropHi = "धान",
            pestOrDiseaseEn = "Blast Disease (Pyricularia oryzae)",
            pestOrDiseaseHi = "धान का झुलसा रोग",
            symptomsEn = "Spindle-shaped spots with grey or white center and brownish-red borders on leaves; neck rot on panicles.",
            symptomsHi = "पत्तियों पर नाव या आंख के आकार के धब्बे जिनका केंद्र धूसर व किनारे भूरे होते हैं; बाली की गर्दन काली पड़ना।",
            organicRemedyEn = "Seed treatment with Pseudomonas fluorescens (10g/kg seed). Spray cow urine extract with turmeric.",
            organicRemedyHi = "स्यूडोमोनास फ्लोरेसेंस (10 ग्राम/किग्रा बीज) से बीज शोधन करें। गोमूत्र और हल्दी का घोल छिड़कें।",
            precautionEn = "Avoid excess nitrogen (urea) fertilizers. Maintain proper water drainage during disease onset.",
            precautionHi = "अत्यधिक यूरिया का प्रयोग न करें। रोग दिखने पर खेत से पानी निकालकर नया पानी भरें।"
        ),
        PestCareItem(
            id = "mustard_aphid",
            cropEn = "Mustard",
            cropHi = "सरसों",
            pestOrDiseaseEn = "Mustard Aphid (Mahu / Chepa)",
            pestOrDiseaseHi = "माहू / चेपा कीट",
            symptomsEn = "Tiny greenish-yellow insects clustering on inflorescence, twigs, and pods, sucking sap causing curling.",
            symptomsHi = "फूलों और फलियों पर हरे-पीले छोटे कीड़ों का झुंड जो रस चूसकर पौधों को कमजोर व चिपचिपा बना देता है।",
            organicRemedyEn = "Spray 2% soap solution or Dashparni Ark in early morning. Conserve natural predators like Ladybird beetles.",
            organicRemedyHi = "सुबह के समय 2% साबुन का घोल या दशपर्णी अर्क छिड़कें। लेडीबर्ड बीटल जैसे मित्र कीटों को संरक्षण दें।",
            precautionEn = "Timely sowing before October 20 drastically escapes aphid peak infestation.",
            precautionHi = "20 अक्टूबर से पूर्व समय पर बुवाई करने से माहू के प्रकोप से काफी हद तक बचा जा सकता है।"
        ),
        PestCareItem(
            id = "gram_pod_borer",
            cropEn = "Chickpea / Pulses",
            cropHi = "चना / दालें",
            pestOrDiseaseEn = "Pod Borer (Helicoverpa armigera)",
            pestOrDiseaseHi = "फली छेदक सुंडी",
            symptomsEn = "Greenish caterpillars eating foliage and boring round holes into pods to consume developing seeds.",
            symptomsHi = "हरी सुंडी जो पत्तियों को खाती है और फलियों में गोल छेद करके अंदर के दानों को चट कर जाती है।",
            organicRemedyEn = "Erect T-shaped bird perches (20 per acre) so birds can feed on caterpillars. Spray Bacillus thuringiensis (Bt) or NPV.",
            organicRemedyHi = "खेत में 'T' आकार की लकड़ी की खूंटियां (20 प्रति एकड़) लगाएं ताकि पक्षी सुंडी खा सकें। Bt या NPV छिड़कें।",
            precautionEn = "Intercrop with coriander or mustard to attract beneficial predatory insects.",
            precautionHi = "चने के साथ धनिया या सरसों की अंतर-फसली खेती करें जो मित्र कीटों को आकर्षित करती है।"
        )
    )

    fun calculateIrrigation(crop: String, soil: String, areaAcres: Double): IrrigationAdvice {
        val baseWaterPerAcre = when (crop.lowercase()) {
            "paddy / rice", "धान" -> 22000L
            "wheat", "गेहूं" -> 9000L
            "cotton", "कपास" -> 11000L
            "sugarcane", "गन्ना" -> 25000L
            "mustard", "सरसों" -> 6000L
            "vegetables", "सब्जियां" -> 8500L
            else -> 8000L
        }

        val soilFactor = when (soil.lowercase()) {
            "sandy", "बलुई" -> 1.35
            "clayey", "चिकनी" -> 0.85
            else -> 1.0 // Loamy
        }

        val totalDailyLiters = (baseWaterPerAcre * areaAcres * soilFactor).toLong()

        val recommendedSystem = when (crop.lowercase()) {
            "paddy / rice", "धान" -> "Alternate Wetting & Drying (AWD) with field pipe monitoring"
            "wheat", "गेहूं", "mustard", "सरसों" -> "Sprinkler Irrigation (Micro/Raingun) - saves 35-40% water"
            "cotton", "कपास", "sugarcane", "गन्ना" -> "Inline Drip Irrigation with fertigation unit - saves 50-60% water"
            else -> "Drip or Micro-Sprinkler System"
        }

        val weeklyFrequency = when (crop.lowercase()) {
            "paddy / rice", "धान" -> "Every 2-3 days (keep 2-3 cm moisture, avoid deep standing water)"
            "wheat", "गेहूं" -> "Every 15-20 days (CRI, Tillering, Jointing, Flowering, Milk, Dough)"
            "mustard", "सरसों" -> "Every 25-30 days (pre-sowing, flowering, pod filling)"
            else -> "Every 3-5 days in summer, 7-10 days in winter"
        }

        val savingsTip = "Installing drip or sprinkler systems qualifies for 45-55% government subsidy under PMKSY."

        return IrrigationAdvice(
            cropName = crop,
            soilType = soil,
            areaAcres = areaAcres,
            dailyRequirementLiters = totalDailyLiters,
            weeklyFrequency = weeklyFrequency,
            recommendedSystem = recommendedSystem,
            savingsTip = savingsTip
        )
    }

    fun getTranslatedUIString(key: String, language: Language): String {
        return when (key) {
            "app_title" -> when (language) {
                Language.HINDI -> "किसान साथी"
                Language.PUNJABI -> "ਕਿਸਾਨ ਸਾਥੀ"
                Language.BENGALI -> "কিসান সাথী"
                Language.GUJARATI -> "કિસાન સાથી"
                Language.MARATHI -> "किसान साथी"
                Language.TAMIL -> "கிசான் சாதி"
                Language.TELUGU -> "కిసాన్ సాథి"
                else -> "Kisaan Sathi"
            }
            "tagline" -> when (language) {
                Language.HINDI -> "स्मार्ट कृषि परामर्श और किसान सहायता"
                Language.PUNJABI -> "ਸਮਾਰਟ ਖੇਤੀ ਸਲਾਹ ਅਤੇ ਕਿਸਾਨ ਸਹਾਇਤਾ"
                Language.BENGALI -> "স্মার্ট কৃষি পরামর্শ এবং কৃষক সহায়তা"
                Language.GUJARATI -> "સ્માર્ટ ખેતી સલાહ અને ખેડૂત સહાય"
                Language.MARATHI -> "स्मार्ट कृषी सल्ला व शेतकरी मदत"
                Language.TAMIL -> "விவசாயிகளுக்கான நவீன வேளாண் வழிகாட்டுதல்"
                Language.TELUGU -> "రైతుల కోసం స్మార్ట్ వ్యవసాయ సలహా"
                else -> "Smart Farming Advisory & Support"
            }
            "emergency_helpline" -> when (language) {
                Language.HINDI -> "किसान कॉल सेंटर (निःशुल्क): 1800-180-1551"
                Language.PUNJABI -> "ਕਿਸਾਨ ਕਾਲ ਸੈਂਟਰ (ਟੋਲ ਫ੍ਰੀ): 1800-180-1551"
                else -> "Kisan Call Center (Toll Free): 1800-180-1551"
            }
            else -> key
        }
    }
}
