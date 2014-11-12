INSERT INTO Drug (drug_name, description, available, license_AEMPS, license_EMA, license_FDA) VALUES ('Furosemide', 'Loop diuretic to treat fluid retention', 1, 'No', 'N.d.', 'Yes');
INSERT INTO Drug (drug_name, description, available, license_AEMPS, license_EMA, license_FDA) VALUES ('Gentamicin', 'Aminoglycoside antibiotic to treat different infections mainly caused by Gram-', 1, 'Yes', 'Yes', 'Yes');
INSERT INTO Code (code_number, anatomic_group, therapeutic_group, drug_name) VALUES ('QC03CA01', 'Cardiovascular system', 'Diuretics', 'Furosemide');
INSERT INTO Code (code_number, anatomic_group, therapeutic_group, drug_name) VALUES ('QA07AA91', 'Alimentary tract and metabolism', 'Antidiarrheals, intestinal anti-inflammatory/antiinfective agents', 'Gentamicin');
INSERT INTO Code (code_number, anatomic_group, therapeutic_group, drug_name) VALUES ('QD06AX07', 'Dermatologicals', 'Antibiotics and chemotherapeutics for dermatological use', 'Gentamicin');
INSERT INTO Code (code_number, anatomic_group, therapeutic_group, drug_name) VALUES ('QG01AA91', 'Genito urinary system and sex hormones', 'Gynecological antiinfectives and antiseptics', 'Gentamicin');
INSERT INTO Code (code_number, anatomic_group, therapeutic_group, drug_name) VALUES ('QJ01GB03', 'Genito urinary system and sex hormones. Antiinfectives for systemic use', 'Antiinfectives and antiseptics for intrauterine use. Antibacterials for sistemic use', 'Gentamicin');
INSERT INTO Code (code_number, anatomic_group, therapeutic_group, drug_name) VALUES ('QJ51GB03', 'Antiinfectives for systemic use', 'Antibacterials for intrammary use', 'Gentamicin');
INSERT INTO Code (code_number, anatomic_group, therapeutic_group, drug_name) VALUES ('QS01AA11', 'Sensory organs', 'Ophtalmologicals', 'Gentamicin');
INSERT INTO Code (code_number, anatomic_group, therapeutic_group, drug_name) VALUES ('QS02AA14', 'Sensory organs', 'Otologicals', 'Gentamicin');
INSERT INTO Code (code_number, anatomic_group, therapeutic_group, drug_name) VALUES ('QS03AA06', 'Sensory organs', 'Ophtalmologicals and otologicals preparations', 'Gentamicin');
INSERT INTO Animal_Type (group_name) VALUES ('Cetaceans');
INSERT INTO Animal_Type (group_name) VALUES ('Pinnipeds');
INSERT INTO Animal_Type (group_name) VALUES ('Other MM');
INSERT INTO Animal (animal_name, family, group_name, drug_name) VALUES ('', '', 'Cetaceans', 'Furosemide');
INSERT INTO Animal (animal_name, family, group_name, drug_name) VALUES ('Dolphins', '', 'Cetaceans', 'Furosemide');
INSERT INTO Animal (animal_name, family, group_name, drug_name) VALUES ('Sea lions', 'Otarids', 'Pinnipeds', 'Furosemide');
INSERT INTO Animal (animal_name, family, group_name, drug_name) VALUES ('', 'Phocids', 'Pinnipeds', 'Furosemide');
INSERT INTO Animal (animal_name, family, group_name, drug_name) VALUES ('Sea otters', '', 'Other MM', 'Furosemide');
INSERT INTO Animal (animal_name, family, group_name, drug_name) VALUES ('', '', 'Cetaceans', 'Gentamicin');
INSERT INTO Animal (animal_name, family, group_name, drug_name) VALUES ('', '', 'Pinnipeds', 'Gentamicin');
INSERT INTO Animal (animal_name, family, group_name, drug_name) VALUES ('Sirenians', '', 'Other MM', 'Gentamicin');
INSERT INTO Animal (animal_name, family, group_name, drug_name) VALUES ('Manatees', '', 'Other MM', 'Gentamicin');
INSERT INTO Animal (animal_name, family, group_name, drug_name) VALUES ('Sea otters', '', 'Other MM', 'Gentamicin');
INSERT INTO Category (category_name) VALUES ('Small odontocetes');
INSERT INTO Category (category_name) VALUES ('General');
INSERT INTO Category (category_name) VALUES ('Pups');
INSERT INTO Category (category_name) VALUES ('Adults');
INSERT INTO Category (category_name) VALUES ('');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Furosemide', 'Cetaceans', 'May increase ototoxicity and nephrotoxicity of cephalosporins (e.g. gentamicin)');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Furosemide', 'Cetaceans', 'Potassium depletion (if concurrent steroid administration consider alternative diuretic)');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Furosemide', 'Cetaceans', 'Increased renal loss of thiamine and pyridoxine');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Furosemide', 'Cetaceans', 'Extreme care with dehydrated animals (as some stranded)');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Furosemide', 'Pinnipeds', 'May increase ototoxicity and nephrotoxicity of cephalosporins (e.g. gentamicin)');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Furosemide', 'Pinnipeds', 'Potassium depletion (if concurrent steroid administration consider alternative diuretic)');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Furosemide', 'Pinnipeds', 'Increased renal loss of thiamine and pyridoxine');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Furosemide', 'Pinnipeds', 'Extreme care with dehydrated animals (as some stranded)');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Furosemide', 'Other MM', 'May increase ototoxicity and nephrotoxicity of cephalosporins (e.g. gentamicin)');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Furosemide', 'Other MM', 'Potassium depletion (if concurrent steroid administration consider alternative diuretic)');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Furosemide', 'Other MM', 'Increased renal loss of thiamine and pyridoxine');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Furosemide', 'Other MM', 'Extreme care with dehydrated animals (as some stranded)');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Gentamicin', 'Cetaceans', 'Ototoxicity and nephrotoxicity are common problems for its clinical use, consider alternative antibacterial');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Gentamicin', 'Cetaceans', 'Ototoxicity and nephrotoxicity caused by gentamicin can be increased by furosemide');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Gentamicin', 'Pinnipeds', 'Ototoxicity and nephrotoxicity are common problems for its clinical use, consider alternative antibacterial');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Gentamicin', 'Pinnipeds', 'Ototoxicity and nephrotoxicity caused by gentamicin can be increased by furosemide');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Gentamicin', 'Other MM', 'Ototoxicity and nephrotoxicity are common problems for its clinical use, consider alternative antibacterial');
INSERT INTO Drug_aplicated_to_Animal_Type (drug_name, group_name, general_note) VALUES ('Gentamicin', 'Other MM', 'Ototoxicity and nephrotoxicity caused by gentamicin can be increased by furosemide');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('', 'Furosemide', '', 'Cetaceans', 'Small odontocetes', 'CRC', 'Consider reduce dose to transport', '', 'IM', '2.0-4.0 mg/kg');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('Dolphins', 'Furosemide', '', 'Cetaceans', '', 'FW5', 'For Bottlenose dolphins', '', 'IM', '2.0-4.0 mg/kg');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('', 'Furosemide', '', 'Cetaceans', '', 'SRM', '', '', 'IM', '2.0-4.0 mg/kg');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('Sea lions', 'Furosemide', 'Otarids', 'Pinnipeds', '', 'CRC', 'For Californian sea lions', 'BID', 'IM, IV, PO', 'up to 5.0 mg/kg');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('', 'Furosemide', 'Phocids', 'Pinnipeds', '', 'CRC', 'For Harbor seals', 'BID', 'IM, IV, PO', 'up to 5.0 mg/kg');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('Sea otters', 'Furosemide', '', 'Other MM', '', 'CRC', 'For Enhydra lutris', '', 'IM', '2.0 mg/kg');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('', 'Gentamicin', '', 'Cetaceans', '', 'CRC', 'Use with caution nephrotoxicity common', 'SID', 'IM', '4.0 mg/kg');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('', 'Gentamicin', '', 'Cetaceans', '', 'CRC', 'Use with caution nephrotoxicity common', '', 'IT', '1.1 mg/kg');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('', 'Gentamicin', '', 'Cetaceans', '', 'CRC', 'Use with caution nephrotoxicity common', 'BID', 'IM', '5.0 mg/kg');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('', 'Gentamicin', '', 'Cetaceans', '', 'CRC', 'Duodenitis treatment in rough toothed dolphin', 'TID', 'PO', '2.5 mg/kg');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('', 'Gentamicin', '', 'Pinnipeds', '', 'CRC', '', 'BID 2d then SID ', 'IT', '0.75 mg/kg');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('Sirenians', 'Gentamicin', '', 'Other MM', '', 'CRC', '', 'SID', 'IM', '4.4 mg/kg');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('Manatees', 'Gentamicin', '', 'Other MM', '', 'CRC', 'Hemorrhagic colitis treatment adjunct to metronidazole', 'TID', 'PO', '2.5 mg/kg');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('Manatees', 'Gentamicin', '', 'Other MM', '', 'FW5', 'For Florida manatees', 'SID', 'IM', '1.0-2.0 mg/kg');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('Sea otters', 'Gentamicin', '', 'Other MM', 'General', 'CRC', 'Nephrotoxicity reported', 'BID', 'IM', '4.4 mg/kg');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('Sea otters', 'Gentamicin', '', 'Other MM', 'Pups', 'CRC', '', 'BID 5d', 'IM', '2.0 mg/kg');
INSERT INTO Animal_has_Category (animal_name, drug_name, family, group_name, category_name, reference, specific_note, posology, route, dose) VALUES ('Sea otters', 'Gentamicin', '', 'Other MM', 'Adults', 'CRC', '', 'TID', 'IM', '2.0 mg/kg');