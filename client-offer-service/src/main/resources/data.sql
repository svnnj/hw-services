-- Insert test client offers
INSERT INTO client_offers (client_id, offer_text) 
VALUES (1, 'Special credit offer with 5% interest rate for loyal customers');

INSERT INTO client_offers (client_id, offer_text) 
VALUES (1, 'Mortgage refinancing opportunity with reduced fees');

INSERT INTO client_offers (client_id, offer_text) 
VALUES (2, 'Personal loan offer with no early repayment penalties');

INSERT INTO client_offers (client_id, offer_text) 
VALUES (3, 'Business expansion loan with flexible repayment terms');

INSERT INTO client_offers (client_id, offer_text) 
VALUES (3, 'Investment opportunity in government bonds with 3.5% yield');

-- Insert reactions to offers
INSERT INTO client_offer_reactions (offer_id, reaction_type) 
VALUES (1, 'ACCEPTED');

INSERT INTO client_offer_reactions (offer_id, reaction_type) 
VALUES (2, 'PENDING');

INSERT INTO client_offer_reactions (offer_id, reaction_type) 
VALUES (3, 'REFUSED');

INSERT INTO client_offer_reactions (offer_id, reaction_type) 
VALUES (4, 'PENDING');

INSERT INTO client_offer_reactions (offer_id, reaction_type) 
VALUES (5, 'ACCEPTED'); 