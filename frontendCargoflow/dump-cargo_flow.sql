--
-- PostgreSQL database dump
--

\restrict U378H8HcHVINg2AUEuBbBKfWb9FQNjJpLKaUtwtNBKmE8YBqy8M5aI78KyKvZAY

-- Dumped from database version 18.1
-- Dumped by pg_dump version 18.1

-- Started on 2026-04-01 14:59:59

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 5287 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS '';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 42367)
-- Name: adr_collo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.adr_collo (
    segnacollo text NOT NULL,
    id_adr_elemento integer NOT NULL,
    codice_imballaggio character varying(5) NOT NULL,
    qta double precision NOT NULL,
    um character varying(5) NOT NULL
);


ALTER TABLE public.adr_collo OWNER TO postgres;

--
-- TOC entry 5289 (class 0 OID 0)
-- Dependencies: 219
-- Name: TABLE adr_collo; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.adr_collo IS 'nel sw considerare "etichettato" e "marcatura un"';


--
-- TOC entry 220 (class 1259 OID 42377)
-- Name: adr_elemento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.adr_elemento (
    numero_un integer NOT NULL,
    denominazione_trasporto character varying(255) NOT NULL,
    classe_adr character varying(5) NOT NULL,
    gruppo_imballaggio character varying(2) NOT NULL,
    codice_galleria character varying(5),
    qta_totale double precision NOT NULL,
    unita_misura character varying(5) NOT NULL,
    esenzione_lq boolean NOT NULL,
    esenzione_eq boolean NOT NULL
);


ALTER TABLE public.adr_elemento OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 42388)
-- Name: assicurazione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.assicurazione (
    id_assicurazione uuid NOT NULL,
    descrizione character varying(25) NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.assicurazione OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 42394)
-- Name: autista; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.autista (
    cod_fisc character varying(20) NOT NULL,
    nome character varying(20) NOT NULL,
    cognome character varying(30) NOT NULL,
    sede uuid NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.autista OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 42402)
-- Name: autorizzazione_integrazione_indirizzo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.autorizzazione_integrazione_indirizzo (
    id uuid CONSTRAINT autorizzazione_indirizzo_id_not_null NOT NULL,
    id_movimento uuid CONSTRAINT autorizzazione_indirizzo_id_movimento_not_null NOT NULL,
    autorizzazione_path character varying(512) CONSTRAINT autorizzazione_indirizzo_autorizzazione_not_null NOT NULL,
    id_azienda uuid CONSTRAINT autorizzazione_indirizzo_id_azienda_not_null NOT NULL
);


ALTER TABLE public.autorizzazione_integrazione_indirizzo OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 42411)
-- Name: azienda; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.azienda (
    id uuid NOT NULL,
    denominazione character varying(25) NOT NULL
);


ALTER TABLE public.azienda OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 42416)
-- Name: bordero; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bordero (
    id_bordero uuid NOT NULL,
    id_autista character varying(20) NOT NULL,
    id_macchina character varying(15) NOT NULL,
    data date NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.bordero OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 42424)
-- Name: bordero_merce; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bordero_merce (
    id_bordero uuid NOT NULL,
    segnacollo text NOT NULL,
    tassativo boolean DEFAULT false CONSTRAINT bordero_merce_tasativo_not_null NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.bordero_merce OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 42434)
-- Name: citta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.citta (
    cap character varying(10) NOT NULL,
    stato character varying(20) NOT NULL,
    citta character varying(25) NOT NULL
);


ALTER TABLE public.citta OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 42440)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    id uuid NOT NULL,
    ragione_sociale character varying(25) NOT NULL,
    indirizzo character varying(60) NOT NULL,
    cap character varying(10) NOT NULL,
    tel character varying(20),
    email character varying(20),
    id_azienda uuid NOT NULL,
    vettore boolean DEFAULT false NOT NULL
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 42448)
-- Name: consegna_avvenuta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.consegna_avvenuta (
    id_movimento uuid NOT NULL,
    firma_path character varying(1024),
    id_azienda uuid NOT NULL,
    cliente_assente boolean NOT NULL,
    autorizzazione_path character varying(1024)
);


ALTER TABLE public.consegna_avvenuta OWNER TO postgres;

--
-- TOC entry 5290 (class 0 OID 0)
-- Dependencies: 229
-- Name: COLUMN consegna_avvenuta.cliente_assente; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.consegna_avvenuta.cliente_assente IS 'se viene consegnato ma non è il destinatario a ritirare oppure viene consegnato presso altro indirizzo autorizzato dal destinatario ma non dal mittente';


--
-- TOC entry 230 (class 1259 OID 42453)
-- Name: consegna_fallita; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.consegna_fallita (
    id bigint NOT NULL,
    id_movimento uuid NOT NULL,
    id_motivo bigint NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.consegna_fallita OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 42460)
-- Name: contrassegno_tipo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contrassegno_tipo (
    id bigint CONSTRAINT tipo_contrassegno_id_not_null NOT NULL,
    descrizione character varying(25) CONSTRAINT tipo_contrassegno_descrizione_not_null NOT NULL
);


ALTER TABLE public.contrassegno_tipo OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 42465)
-- Name: epal_cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.epal_cliente (
    id_cliente uuid NOT NULL,
    id uuid NOT NULL,
    addebito_epal integer NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.epal_cliente OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 42472)
-- Name: fasce_orarie; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fasce_orarie (
    id uuid CONSTRAINT fasce_orarie_id_not_null1 NOT NULL,
    dalle timestamp without time zone,
    alle timestamp without time zone
);


ALTER TABLE public.fasce_orarie OWNER TO postgres;

--
-- TOC entry 256 (class 1259 OID 68589)
-- Name: fasce_orarie_cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fasce_orarie_cliente (
    id uuid NOT NULL,
    cliente uuid CONSTRAINT fasce_orarie_cliente_incarico_not_null NOT NULL,
    fascia_oraria uuid,
    giorno uuid
);


ALTER TABLE public.fasce_orarie_cliente OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 42490)
-- Name: giorno_preferito; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.giorno_preferito (
    id uuid NOT NULL,
    giorno integer NOT NULL
);


ALTER TABLE public.giorno_preferito OWNER TO postgres;

--
-- TOC entry 5291 (class 0 OID 0)
-- Dependencies: 235
-- Name: COLUMN giorno_preferito.giorno; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.giorno_preferito.giorno IS 'i giorni vanno da 1 a 7';


--
-- TOC entry 236 (class 1259 OID 42495)
-- Name: incarico; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.incarico (
    ldv text NOT NULL,
    ddt text,
    data_consegna_prevista date NOT NULL,
    valore_doganale double precision,
    ritiro boolean DEFAULT false NOT NULL,
    valore_assicurazione double precision,
    mitt uuid NOT NULL,
    dest uuid NOT NULL,
    child text,
    vett_mitt uuid,
    id_azienda uuid NOT NULL,
    vett_dest uuid,
    contrassegno boolean NOT NULL,
    contrassegno_valore double precision,
    contrassegno_tipo bigint,
    note text
);


ALTER TABLE public.incarico OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 42485)
-- Name: incarico_fasce_orarie; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.incarico_fasce_orarie (
    id_giorno uuid,
    id_fascia_oraria uuid,
    incarico text CONSTRAINT giorno_fasce_orarie_incarico_not_null NOT NULL,
    id uuid CONSTRAINT giorno_fasce_orarie_id_not_null NOT NULL,
    "timestamp" timestamp without time zone DEFAULT now() CONSTRAINT giorno_fasce_orarie_timestamp_not_null NOT NULL
);


ALTER TABLE public.incarico_fasce_orarie OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 42516)
-- Name: incarico_sede_mitt_dest; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.incarico_sede_mitt_dest (
    incarico text CONSTRAINT incarico_sede_incarico_not_null NOT NULL,
    sede_mitt uuid CONSTRAINT incarico_sede_sede_mitt_not_null NOT NULL,
    sede_dest uuid CONSTRAINT incarico_sede_sede_dest_not_null NOT NULL,
    dal date CONSTRAINT incarico_sede_dal_not_null NOT NULL,
    fino_a date,
    id uuid NOT NULL,
    autorizzazione bigint,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.incarico_sede_mitt_dest OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 42527)
-- Name: manutenzione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.manutenzione (
    id_manutenzione uuid NOT NULL,
    targa_veicolo text NOT NULL,
    id_officina uuid,
    km integer NOT NULL,
    descrizione text NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.manutenzione OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 42537)
-- Name: merce; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.merce (
    segnacollo text NOT NULL,
    incarico text NOT NULL,
    merce_tipo bigint NOT NULL,
    peso_kg double precision,
    volume_m3 double precision,
    fragile boolean DEFAULT false,
    epal boolean NOT NULL,
    id_epal character varying(50)
);


ALTER TABLE public.merce OWNER TO postgres;

--
-- TOC entry 5292 (class 0 OID 0)
-- Dependencies: 239
-- Name: COLUMN merce.fragile; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.merce.fragile IS 'implica impilabilità';


--
-- TOC entry 240 (class 1259 OID 42554)
-- Name: merce_tipo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.merce_tipo (
    id bigint NOT NULL,
    descrizione character varying(25) NOT NULL
);


ALTER TABLE public.merce_tipo OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 42559)
-- Name: merce_tipo_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.merce_tipo ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.merce_tipo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 242 (class 1259 OID 42560)
-- Name: officina; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.officina (
    id uuid NOT NULL,
    ragione_sociale character varying(25) NOT NULL,
    indirizzo character varying(30) NOT NULL,
    cap character varying(10) NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.officina OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 42568)
-- Name: rinnovo_assicurazione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rinnovo_assicurazione (
    id uuid NOT NULL,
    targa_veicolo character varying(10) NOT NULL,
    id_assicurazione uuid NOT NULL,
    dal date NOT NULL,
    fino_al date NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.rinnovo_assicurazione OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 42577)
-- Name: rinnovo_bollo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rinnovo_bollo (
    id uuid NOT NULL,
    targa_veicolo character varying(10) NOT NULL,
    dal date NOT NULL,
    fino_al date NOT NULL,
    importo double precision,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.rinnovo_bollo OWNER TO postgres;

--
-- TOC entry 245 (class 1259 OID 42585)
-- Name: rinnovo_patente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rinnovo_patente (
    id uuid NOT NULL,
    id_autista character varying(20) NOT NULL,
    patente_tipo character varying(20) NOT NULL,
    dal date NOT NULL,
    al date NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.rinnovo_patente OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 42594)
-- Name: riserva; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.riserva (
    segnacollo text NOT NULL,
    descrizione character varying(1024) NOT NULL,
    firma character varying(512),
    id_azienda uuid NOT NULL,
    movimento uuid NOT NULL
);


ALTER TABLE public.riserva OWNER TO postgres;

--
-- TOC entry 247 (class 1259 OID 42602)
-- Name: sede; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sede (
    id uuid NOT NULL,
    sede character varying(50) NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.sede OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 42608)
-- Name: segnacollo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.segnacollo (
    segnacollo text NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.segnacollo OWNER TO postgres;

--
-- TOC entry 249 (class 1259 OID 42615)
-- Name: sessione_carico_scarico; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sessione_carico_scarico (
    id uuid CONSTRAINT sessione_carico_id_not_null NOT NULL,
    id_bordero uuid,
    vettore uuid,
    storico uuid CONSTRAINT sessione_carico_storico_not_null NOT NULL,
    note text,
    carico boolean NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.sessione_carico_scarico OWNER TO postgres;

--
-- TOC entry 5293 (class 0 OID 0)
-- Dependencies: 249
-- Name: TABLE sessione_carico_scarico; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.sessione_carico_scarico IS 'le informazioni di questa tabella servono per aggiornare lo storico';


--
-- TOC entry 250 (class 1259 OID 42624)
-- Name: stato; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stato (
    id bigint NOT NULL,
    descrizione character varying(25) NOT NULL,
    parent bigint
);


ALTER TABLE public.stato OWNER TO postgres;

--
-- TOC entry 251 (class 1259 OID 42629)
-- Name: stato_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.stato ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.stato_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 252 (class 1259 OID 42630)
-- Name: storico; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.storico (
    id_movimento uuid NOT NULL,
    segnacollo text CONSTRAINT storico_incarico_not_null NOT NULL,
    id_stato bigint NOT NULL,
    data_ora timestamp without time zone NOT NULL,
    sede uuid NOT NULL,
    id_azienda uuid NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.storico OWNER TO postgres;

--
-- TOC entry 253 (class 1259 OID 42643)
-- Name: tipo_contrassegno_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.contrassegno_tipo ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tipo_contrassegno_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 254 (class 1259 OID 42644)
-- Name: veicolo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.veicolo (
    targa character varying(10) NOT NULL,
    telaio character varying(100),
    sede uuid NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.veicolo OWNER TO postgres;

--
-- TOC entry 5294 (class 0 OID 0)
-- Dependencies: 254
-- Name: TABLE veicolo; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.veicolo IS 'gli oggetti che si trovano solo in quest tabella e non anche in veicolo_lavoro sono mezzi personali';


--
-- TOC entry 255 (class 1259 OID 42650)
-- Name: veicolo_lavoro; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.veicolo_lavoro (
    targa character varying(10) NOT NULL,
    lato_corto real NOT NULL,
    lato_lungo real NOT NULL,
    altezza real NOT NULL,
    quintali_max real NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.veicolo_lavoro OWNER TO postgres;

--
-- TOC entry 5244 (class 0 OID 42367)
-- Dependencies: 219
-- Data for Name: adr_collo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5245 (class 0 OID 42377)
-- Dependencies: 220
-- Data for Name: adr_elemento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5246 (class 0 OID 42388)
-- Dependencies: 221
-- Data for Name: assicurazione; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5247 (class 0 OID 42394)
-- Dependencies: 222
-- Data for Name: autista; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5248 (class 0 OID 42402)
-- Dependencies: 223
-- Data for Name: autorizzazione_integrazione_indirizzo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5249 (class 0 OID 42411)
-- Dependencies: 224
-- Data for Name: azienda; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5250 (class 0 OID 42416)
-- Dependencies: 225
-- Data for Name: bordero; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5251 (class 0 OID 42424)
-- Dependencies: 226
-- Data for Name: bordero_merce; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5252 (class 0 OID 42434)
-- Dependencies: 227
-- Data for Name: citta; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5253 (class 0 OID 42440)
-- Dependencies: 228
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5254 (class 0 OID 42448)
-- Dependencies: 229
-- Data for Name: consegna_avvenuta; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5255 (class 0 OID 42453)
-- Dependencies: 230
-- Data for Name: consegna_fallita; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5256 (class 0 OID 42460)
-- Dependencies: 231
-- Data for Name: contrassegno_tipo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5257 (class 0 OID 42465)
-- Dependencies: 232
-- Data for Name: epal_cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5258 (class 0 OID 42472)
-- Dependencies: 233
-- Data for Name: fasce_orarie; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5281 (class 0 OID 68589)
-- Dependencies: 256
-- Data for Name: fasce_orarie_cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5260 (class 0 OID 42490)
-- Dependencies: 235
-- Data for Name: giorno_preferito; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5261 (class 0 OID 42495)
-- Dependencies: 236
-- Data for Name: incarico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5259 (class 0 OID 42485)
-- Dependencies: 234
-- Data for Name: incarico_fasce_orarie; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5262 (class 0 OID 42516)
-- Dependencies: 237
-- Data for Name: incarico_sede_mitt_dest; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5263 (class 0 OID 42527)
-- Dependencies: 238
-- Data for Name: manutenzione; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5264 (class 0 OID 42537)
-- Dependencies: 239
-- Data for Name: merce; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5265 (class 0 OID 42554)
-- Dependencies: 240
-- Data for Name: merce_tipo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.merce_tipo OVERRIDING SYSTEM VALUE VALUES (1, 'COLLO');
INSERT INTO public.merce_tipo OVERRIDING SYSTEM VALUE VALUES (2, 'BANCALE');


--
-- TOC entry 5267 (class 0 OID 42560)
-- Dependencies: 242
-- Data for Name: officina; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5268 (class 0 OID 42568)
-- Dependencies: 243
-- Data for Name: rinnovo_assicurazione; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5269 (class 0 OID 42577)
-- Dependencies: 244
-- Data for Name: rinnovo_bollo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5270 (class 0 OID 42585)
-- Dependencies: 245
-- Data for Name: rinnovo_patente; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5271 (class 0 OID 42594)
-- Dependencies: 246
-- Data for Name: riserva; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5272 (class 0 OID 42602)
-- Dependencies: 247
-- Data for Name: sede; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5273 (class 0 OID 42608)
-- Dependencies: 248
-- Data for Name: segnacollo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5274 (class 0 OID 42615)
-- Dependencies: 249
-- Data for Name: sessione_carico_scarico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5275 (class 0 OID 42624)
-- Dependencies: 250
-- Data for Name: stato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5277 (class 0 OID 42630)
-- Dependencies: 252
-- Data for Name: storico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5279 (class 0 OID 42644)
-- Dependencies: 254
-- Data for Name: veicolo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5280 (class 0 OID 42650)
-- Dependencies: 255
-- Data for Name: veicolo_lavoro; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5295 (class 0 OID 0)
-- Dependencies: 241
-- Name: merce_tipo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.merce_tipo_id_seq', 2, true);


--
-- TOC entry 5296 (class 0 OID 0)
-- Dependencies: 251
-- Name: stato_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.stato_id_seq', 1, false);


--
-- TOC entry 5297 (class 0 OID 0)
-- Dependencies: 253
-- Name: tipo_contrassegno_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_contrassegno_id_seq', 1, false);


--
-- TOC entry 4954 (class 2606 OID 42660)
-- Name: adr_collo adr_collo_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adr_collo
    ADD CONSTRAINT adr_collo_pk PRIMARY KEY (segnacollo);


--
-- TOC entry 4956 (class 2606 OID 42662)
-- Name: adr_elemento adr_elemento_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adr_elemento
    ADD CONSTRAINT adr_elemento_pk PRIMARY KEY (numero_un);


--
-- TOC entry 4958 (class 2606 OID 42664)
-- Name: assicurazione assicurazione_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assicurazione
    ADD CONSTRAINT assicurazione_pk PRIMARY KEY (id_assicurazione);


--
-- TOC entry 4960 (class 2606 OID 42666)
-- Name: autista autista_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autista
    ADD CONSTRAINT autista_pk PRIMARY KEY (cod_fisc);


--
-- TOC entry 4962 (class 2606 OID 42668)
-- Name: autorizzazione_integrazione_indirizzo autorizzazione_indirizzo_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autorizzazione_integrazione_indirizzo
    ADD CONSTRAINT autorizzazione_indirizzo_pk PRIMARY KEY (id);


--
-- TOC entry 4964 (class 2606 OID 42670)
-- Name: azienda azienda_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.azienda
    ADD CONSTRAINT azienda_pk PRIMARY KEY (id);


--
-- TOC entry 4968 (class 2606 OID 42672)
-- Name: bordero_merce bordero_merce_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bordero_merce
    ADD CONSTRAINT bordero_merce_pk PRIMARY KEY (id_bordero, segnacollo);


--
-- TOC entry 4966 (class 2606 OID 42674)
-- Name: bordero bordero_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bordero
    ADD CONSTRAINT bordero_pk PRIMARY KEY (id_bordero);


--
-- TOC entry 4970 (class 2606 OID 42676)
-- Name: citta citta_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.citta
    ADD CONSTRAINT citta_pk PRIMARY KEY (cap);


--
-- TOC entry 4972 (class 2606 OID 42678)
-- Name: cliente cliente_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pk PRIMARY KEY (id);


--
-- TOC entry 4974 (class 2606 OID 42680)
-- Name: consegna_avvenuta consegna_avvenuta_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consegna_avvenuta
    ADD CONSTRAINT consegna_avvenuta_pk PRIMARY KEY (id_movimento);


--
-- TOC entry 4976 (class 2606 OID 42682)
-- Name: consegna_fallita consegna_fallita_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consegna_fallita
    ADD CONSTRAINT consegna_fallita_pk PRIMARY KEY (id);


--
-- TOC entry 4980 (class 2606 OID 42684)
-- Name: epal_cliente epal_cliente_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.epal_cliente
    ADD CONSTRAINT epal_cliente_pk PRIMARY KEY (id);


--
-- TOC entry 5024 (class 2606 OID 68599)
-- Name: fasce_orarie_cliente fasce_orarie_pk_1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fasce_orarie_cliente
    ADD CONSTRAINT fasce_orarie_pk_1 PRIMARY KEY (id);


--
-- TOC entry 4982 (class 2606 OID 42688)
-- Name: fasce_orarie fascia_oraria_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fasce_orarie
    ADD CONSTRAINT fascia_oraria_pk PRIMARY KEY (id);


--
-- TOC entry 4984 (class 2606 OID 68687)
-- Name: incarico_fasce_orarie giorno_fasce_orarie_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_fasce_orarie
    ADD CONSTRAINT giorno_fasce_orarie_pk PRIMARY KEY (incarico, id);


--
-- TOC entry 4986 (class 2606 OID 42692)
-- Name: giorno_preferito giorno_preferito_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.giorno_preferito
    ADD CONSTRAINT giorno_preferito_pk PRIMARY KEY (id);


--
-- TOC entry 4988 (class 2606 OID 42696)
-- Name: incarico incarico_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico
    ADD CONSTRAINT incarico_pk PRIMARY KEY (ldv);


--
-- TOC entry 4990 (class 2606 OID 42698)
-- Name: incarico_sede_mitt_dest incarico_sede_mitt_dest_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_sede_mitt_dest
    ADD CONSTRAINT incarico_sede_mitt_dest_pk PRIMARY KEY (id);


--
-- TOC entry 4992 (class 2606 OID 42700)
-- Name: manutenzione manutenzione_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manutenzione
    ADD CONSTRAINT manutenzione_pk PRIMARY KEY (id_manutenzione);


--
-- TOC entry 4994 (class 2606 OID 42704)
-- Name: merce merce_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merce
    ADD CONSTRAINT merce_pk PRIMARY KEY (segnacollo);


--
-- TOC entry 4996 (class 2606 OID 42706)
-- Name: merce_tipo merce_tipo_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merce_tipo
    ADD CONSTRAINT merce_tipo_pk PRIMARY KEY (id);


--
-- TOC entry 4998 (class 2606 OID 42708)
-- Name: officina officina_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.officina
    ADD CONSTRAINT officina_pk PRIMARY KEY (id);


--
-- TOC entry 5002 (class 2606 OID 42710)
-- Name: rinnovo_bollo rinnovo_bollo_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_bollo
    ADD CONSTRAINT rinnovo_bollo_pk PRIMARY KEY (id);


--
-- TOC entry 5004 (class 2606 OID 42712)
-- Name: rinnovo_patente rinnovo_patente_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_patente
    ADD CONSTRAINT rinnovo_patente_pk PRIMARY KEY (id);


--
-- TOC entry 5006 (class 2606 OID 42714)
-- Name: riserva riserva_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.riserva
    ADD CONSTRAINT riserva_pk PRIMARY KEY (segnacollo);


--
-- TOC entry 5008 (class 2606 OID 42716)
-- Name: sede sede_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sede
    ADD CONSTRAINT sede_pk PRIMARY KEY (id);


--
-- TOC entry 5010 (class 2606 OID 42718)
-- Name: segnacollo segnacollo_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.segnacollo
    ADD CONSTRAINT segnacollo_pk PRIMARY KEY (segnacollo);


--
-- TOC entry 5012 (class 2606 OID 42720)
-- Name: sessione_carico_scarico sessione_carico_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione_carico_scarico
    ADD CONSTRAINT sessione_carico_pk PRIMARY KEY (id);


--
-- TOC entry 5014 (class 2606 OID 42722)
-- Name: stato stato_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stato
    ADD CONSTRAINT stato_pk PRIMARY KEY (id);


--
-- TOC entry 5016 (class 2606 OID 42724)
-- Name: stato stato_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stato
    ADD CONSTRAINT stato_unique UNIQUE (descrizione);


--
-- TOC entry 5018 (class 2606 OID 42726)
-- Name: storico storico_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico
    ADD CONSTRAINT storico_pk PRIMARY KEY (id_movimento);


--
-- TOC entry 4978 (class 2606 OID 42728)
-- Name: contrassegno_tipo tipo_contrassegno_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contrassegno_tipo
    ADD CONSTRAINT tipo_contrassegno_pk PRIMARY KEY (id);


--
-- TOC entry 5000 (class 2606 OID 42730)
-- Name: rinnovo_assicurazione veicolo_assicurazione_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_assicurazione
    ADD CONSTRAINT veicolo_assicurazione_pk PRIMARY KEY (id);


--
-- TOC entry 5022 (class 2606 OID 42732)
-- Name: veicolo_lavoro veicolo_lavoro_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.veicolo_lavoro
    ADD CONSTRAINT veicolo_lavoro_pk PRIMARY KEY (targa);


--
-- TOC entry 5020 (class 2606 OID 42734)
-- Name: veicolo veicolo_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.veicolo
    ADD CONSTRAINT veicolo_pk PRIMARY KEY (targa);


--
-- TOC entry 5025 (class 2606 OID 42735)
-- Name: adr_collo adr_collo_adr_elemento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adr_collo
    ADD CONSTRAINT adr_collo_adr_elemento_fk FOREIGN KEY (id_adr_elemento) REFERENCES public.adr_elemento(numero_un);


--
-- TOC entry 5026 (class 2606 OID 42740)
-- Name: adr_collo adr_collo_merce_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adr_collo
    ADD CONSTRAINT adr_collo_merce_fk FOREIGN KEY (segnacollo) REFERENCES public.merce(segnacollo);


--
-- TOC entry 5027 (class 2606 OID 42745)
-- Name: assicurazione assicurazione_azienda_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assicurazione
    ADD CONSTRAINT assicurazione_azienda_fk FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5028 (class 2606 OID 42750)
-- Name: autista autista_azienda_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autista
    ADD CONSTRAINT autista_azienda_fk FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5029 (class 2606 OID 42755)
-- Name: autista autista_sede_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autista
    ADD CONSTRAINT autista_sede_fk FOREIGN KEY (sede) REFERENCES public.sede(id);


--
-- TOC entry 5030 (class 2606 OID 42760)
-- Name: autorizzazione_integrazione_indirizzo autorizzazione_indirizzo_azienda_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autorizzazione_integrazione_indirizzo
    ADD CONSTRAINT autorizzazione_indirizzo_azienda_fk FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5031 (class 2606 OID 42765)
-- Name: autorizzazione_integrazione_indirizzo autorizzazione_indirizzo_storico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autorizzazione_integrazione_indirizzo
    ADD CONSTRAINT autorizzazione_indirizzo_storico_fk FOREIGN KEY (id_movimento) REFERENCES public.storico(id_movimento);


--
-- TOC entry 5032 (class 2606 OID 42770)
-- Name: bordero bordero_autista_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bordero
    ADD CONSTRAINT bordero_autista_fk FOREIGN KEY (id_autista) REFERENCES public.autista(cod_fisc);


--
-- TOC entry 5033 (class 2606 OID 42775)
-- Name: bordero bordero_azienda_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bordero
    ADD CONSTRAINT bordero_azienda_fk FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5035 (class 2606 OID 42780)
-- Name: bordero_merce bordero_merce_bordero_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bordero_merce
    ADD CONSTRAINT bordero_merce_bordero_fk FOREIGN KEY (id_bordero) REFERENCES public.bordero(id_bordero);


--
-- TOC entry 5036 (class 2606 OID 42785)
-- Name: bordero_merce bordero_merce_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bordero_merce
    ADD CONSTRAINT bordero_merce_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5037 (class 2606 OID 42790)
-- Name: bordero_merce bordero_merce_merce_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bordero_merce
    ADD CONSTRAINT bordero_merce_merce_fk FOREIGN KEY (segnacollo) REFERENCES public.merce(segnacollo);


--
-- TOC entry 5034 (class 2606 OID 42795)
-- Name: bordero bordero_veicolo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bordero
    ADD CONSTRAINT bordero_veicolo_fk FOREIGN KEY (id_macchina) REFERENCES public.veicolo(targa);


--
-- TOC entry 5038 (class 2606 OID 42800)
-- Name: cliente cliente_citta_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_citta_fk FOREIGN KEY (cap) REFERENCES public.citta(cap);


--
-- TOC entry 5039 (class 2606 OID 42805)
-- Name: cliente cliente_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5040 (class 2606 OID 42810)
-- Name: consegna_avvenuta consegna_avvenuta_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consegna_avvenuta
    ADD CONSTRAINT consegna_avvenuta_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5041 (class 2606 OID 42815)
-- Name: consegna_avvenuta consegna_avvenuta_storico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consegna_avvenuta
    ADD CONSTRAINT consegna_avvenuta_storico_fk FOREIGN KEY (id_movimento) REFERENCES public.storico(id_movimento);


--
-- TOC entry 5042 (class 2606 OID 42820)
-- Name: consegna_fallita consegna_fallita_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consegna_fallita
    ADD CONSTRAINT consegna_fallita_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5043 (class 2606 OID 42825)
-- Name: consegna_fallita consegna_fallita_stato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consegna_fallita
    ADD CONSTRAINT consegna_fallita_stato_fk FOREIGN KEY (id) REFERENCES public.stato(id);


--
-- TOC entry 5044 (class 2606 OID 42830)
-- Name: consegna_fallita consegna_fallita_storico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consegna_fallita
    ADD CONSTRAINT consegna_fallita_storico_fk FOREIGN KEY (id_movimento) REFERENCES public.storico(id_movimento);


--
-- TOC entry 5045 (class 2606 OID 42835)
-- Name: epal_cliente epal_cliente_cliente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.epal_cliente
    ADD CONSTRAINT epal_cliente_cliente_fk FOREIGN KEY (id) REFERENCES public.cliente(id);


--
-- TOC entry 5046 (class 2606 OID 42840)
-- Name: epal_cliente epal_cliente_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.epal_cliente
    ADD CONSTRAINT epal_cliente_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5095 (class 2606 OID 68650)
-- Name: fasce_orarie_cliente fasce_orarie_cliente_fasce_orarie_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fasce_orarie_cliente
    ADD CONSTRAINT fasce_orarie_cliente_fasce_orarie_fk FOREIGN KEY (fascia_oraria) REFERENCES public.fasce_orarie(id);


--
-- TOC entry 5096 (class 2606 OID 68645)
-- Name: fasce_orarie_cliente fasce_orarie_cliente_giorno_preferito_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fasce_orarie_cliente
    ADD CONSTRAINT fasce_orarie_cliente_giorno_preferito_fk FOREIGN KEY (giorno) REFERENCES public.giorno_preferito(id);


--
-- TOC entry 5047 (class 2606 OID 42845)
-- Name: fasce_orarie fasce_orarie_giorno_preferito_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fasce_orarie
    ADD CONSTRAINT fasce_orarie_giorno_preferito_fk FOREIGN KEY (id) REFERENCES public.giorno_preferito(id);


--
-- TOC entry 5058 (class 2606 OID 42865)
-- Name: incarico_sede_mitt_dest fk_ismd_dest; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_sede_mitt_dest
    ADD CONSTRAINT fk_ismd_dest FOREIGN KEY (sede_dest) REFERENCES public.cliente(id);


--
-- TOC entry 5059 (class 2606 OID 42870)
-- Name: incarico_sede_mitt_dest fk_ismd_incarico; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_sede_mitt_dest
    ADD CONSTRAINT fk_ismd_incarico FOREIGN KEY (incarico) REFERENCES public.incarico(ldv);


--
-- TOC entry 5060 (class 2606 OID 42875)
-- Name: incarico_sede_mitt_dest fk_ismd_mitt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_sede_mitt_dest
    ADD CONSTRAINT fk_ismd_mitt FOREIGN KEY (sede_mitt) REFERENCES public.cliente(id);


--
-- TOC entry 5048 (class 2606 OID 42880)
-- Name: incarico_fasce_orarie giorno_fasce_orarie_fasce_orarie_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_fasce_orarie
    ADD CONSTRAINT giorno_fasce_orarie_fasce_orarie_fk FOREIGN KEY (id_fascia_oraria) REFERENCES public.fasce_orarie(id);


--
-- TOC entry 5049 (class 2606 OID 42885)
-- Name: incarico_fasce_orarie giorno_fasce_orarie_giorno_preferito_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_fasce_orarie
    ADD CONSTRAINT giorno_fasce_orarie_giorno_preferito_fk FOREIGN KEY (id_giorno) REFERENCES public.giorno_preferito(id);


--
-- TOC entry 5050 (class 2606 OID 68637)
-- Name: incarico_fasce_orarie giorno_fasce_orarie_incarico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_fasce_orarie
    ADD CONSTRAINT giorno_fasce_orarie_incarico_fk FOREIGN KEY (incarico) REFERENCES public.incarico(ldv);


--
-- TOC entry 5051 (class 2606 OID 42890)
-- Name: incarico incarico_cliente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico
    ADD CONSTRAINT incarico_cliente_fk FOREIGN KEY (mitt) REFERENCES public.cliente(id);


--
-- TOC entry 5052 (class 2606 OID 42895)
-- Name: incarico incarico_cliente_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico
    ADD CONSTRAINT incarico_cliente_fk_1 FOREIGN KEY (dest) REFERENCES public.cliente(id);


--
-- TOC entry 5053 (class 2606 OID 42900)
-- Name: incarico incarico_cliente_vett_dest_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico
    ADD CONSTRAINT incarico_cliente_vett_dest_fk FOREIGN KEY (vett_dest) REFERENCES public.cliente(id);


--
-- TOC entry 5054 (class 2606 OID 43123)
-- Name: incarico incarico_contrassegno_tipo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico
    ADD CONSTRAINT incarico_contrassegno_tipo_fk FOREIGN KEY (contrassegno_tipo) REFERENCES public.contrassegno_tipo(id);


--
-- TOC entry 5055 (class 2606 OID 42920)
-- Name: incarico incarico_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico
    ADD CONSTRAINT incarico_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5056 (class 2606 OID 42925)
-- Name: incarico incarico_incarico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico
    ADD CONSTRAINT incarico_incarico_fk FOREIGN KEY (child) REFERENCES public.incarico(ldv);


--
-- TOC entry 5061 (class 2606 OID 42930)
-- Name: incarico_sede_mitt_dest incarico_sede_mitt_dest_autorizzazione_indirizzo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_sede_mitt_dest
    ADD CONSTRAINT incarico_sede_mitt_dest_autorizzazione_indirizzo_fk FOREIGN KEY (id) REFERENCES public.autorizzazione_integrazione_indirizzo(id);


--
-- TOC entry 5062 (class 2606 OID 42935)
-- Name: incarico_sede_mitt_dest incarico_sede_mitt_dest_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_sede_mitt_dest
    ADD CONSTRAINT incarico_sede_mitt_dest_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5057 (class 2606 OID 42940)
-- Name: incarico incarico_vett_mitt_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico
    ADD CONSTRAINT incarico_vett_mitt_fk FOREIGN KEY (vett_mitt) REFERENCES public.cliente(id);


--
-- TOC entry 5063 (class 2606 OID 42945)
-- Name: manutenzione manutenzione_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manutenzione
    ADD CONSTRAINT manutenzione_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5064 (class 2606 OID 42950)
-- Name: manutenzione manutenzione_officina_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manutenzione
    ADD CONSTRAINT manutenzione_officina_fk FOREIGN KEY (id_officina) REFERENCES public.officina(id);


--
-- TOC entry 5065 (class 2606 OID 42955)
-- Name: manutenzione manutenzione_veicolo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manutenzione
    ADD CONSTRAINT manutenzione_veicolo_fk FOREIGN KEY (targa_veicolo) REFERENCES public.veicolo(targa);


--
-- TOC entry 5066 (class 2606 OID 42975)
-- Name: merce merce_incarico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merce
    ADD CONSTRAINT merce_incarico_fk FOREIGN KEY (incarico) REFERENCES public.incarico(ldv);


--
-- TOC entry 5067 (class 2606 OID 42980)
-- Name: merce merce_merce_tipo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merce
    ADD CONSTRAINT merce_merce_tipo_fk FOREIGN KEY (merce_tipo) REFERENCES public.merce_tipo(id);


--
-- TOC entry 5068 (class 2606 OID 42985)
-- Name: merce merce_segnacollo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merce
    ADD CONSTRAINT merce_segnacollo_fk FOREIGN KEY (segnacollo) REFERENCES public.segnacollo(segnacollo);


--
-- TOC entry 5069 (class 2606 OID 42990)
-- Name: officina officina_citta_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.officina
    ADD CONSTRAINT officina_citta_fk FOREIGN KEY (cap) REFERENCES public.citta(cap);


--
-- TOC entry 5070 (class 2606 OID 42995)
-- Name: officina officina_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.officina
    ADD CONSTRAINT officina_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5071 (class 2606 OID 43000)
-- Name: rinnovo_assicurazione rinnovo_assicurazione_assicurazione_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_assicurazione
    ADD CONSTRAINT rinnovo_assicurazione_assicurazione_fk FOREIGN KEY (id_assicurazione) REFERENCES public.assicurazione(id_assicurazione);


--
-- TOC entry 5072 (class 2606 OID 43005)
-- Name: rinnovo_assicurazione rinnovo_assicurazione_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_assicurazione
    ADD CONSTRAINT rinnovo_assicurazione_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5073 (class 2606 OID 43010)
-- Name: rinnovo_assicurazione rinnovo_assicurazione_veicolo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_assicurazione
    ADD CONSTRAINT rinnovo_assicurazione_veicolo_fk FOREIGN KEY (targa_veicolo) REFERENCES public.veicolo(targa);


--
-- TOC entry 5074 (class 2606 OID 43015)
-- Name: rinnovo_bollo rinnovo_bollo_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_bollo
    ADD CONSTRAINT rinnovo_bollo_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5075 (class 2606 OID 43020)
-- Name: rinnovo_bollo rinnovo_bollo_veicolo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_bollo
    ADD CONSTRAINT rinnovo_bollo_veicolo_fk FOREIGN KEY (targa_veicolo) REFERENCES public.veicolo(targa);


--
-- TOC entry 5076 (class 2606 OID 43025)
-- Name: rinnovo_patente rinnovo_patente_autista_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_patente
    ADD CONSTRAINT rinnovo_patente_autista_fk FOREIGN KEY (id_autista) REFERENCES public.autista(cod_fisc);


--
-- TOC entry 5077 (class 2606 OID 43030)
-- Name: rinnovo_patente rinnovo_patente_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_patente
    ADD CONSTRAINT rinnovo_patente_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5078 (class 2606 OID 43035)
-- Name: riserva riserva_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.riserva
    ADD CONSTRAINT riserva_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5079 (class 2606 OID 43040)
-- Name: riserva riserva_merce_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.riserva
    ADD CONSTRAINT riserva_merce_fk FOREIGN KEY (segnacollo) REFERENCES public.merce(segnacollo);


--
-- TOC entry 5080 (class 2606 OID 43045)
-- Name: sede sede_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sede
    ADD CONSTRAINT sede_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5081 (class 2606 OID 43050)
-- Name: segnacollo segnacollo_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.segnacollo
    ADD CONSTRAINT segnacollo_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5082 (class 2606 OID 43055)
-- Name: sessione_carico_scarico sessione_carico_bordero_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione_carico_scarico
    ADD CONSTRAINT sessione_carico_bordero_fk FOREIGN KEY (id_bordero) REFERENCES public.bordero(id_bordero);


--
-- TOC entry 5083 (class 2606 OID 43060)
-- Name: sessione_carico_scarico sessione_carico_cliente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione_carico_scarico
    ADD CONSTRAINT sessione_carico_cliente_fk FOREIGN KEY (vettore) REFERENCES public.cliente(id);


--
-- TOC entry 5084 (class 2606 OID 43065)
-- Name: sessione_carico_scarico sessione_carico_scarico_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione_carico_scarico
    ADD CONSTRAINT sessione_carico_scarico_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5085 (class 2606 OID 43070)
-- Name: sessione_carico_scarico sessione_carico_storico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione_carico_scarico
    ADD CONSTRAINT sessione_carico_storico_fk FOREIGN KEY (storico) REFERENCES public.storico(id_movimento);


--
-- TOC entry 5086 (class 2606 OID 43075)
-- Name: stato stato_stato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stato
    ADD CONSTRAINT stato_stato_fk FOREIGN KEY (parent) REFERENCES public.stato(id);


--
-- TOC entry 5087 (class 2606 OID 43080)
-- Name: storico storico_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico
    ADD CONSTRAINT storico_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5088 (class 2606 OID 43085)
-- Name: storico storico_sede_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico
    ADD CONSTRAINT storico_sede_fk FOREIGN KEY (sede) REFERENCES public.sede(id);


--
-- TOC entry 5089 (class 2606 OID 43090)
-- Name: storico storico_segnacollo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico
    ADD CONSTRAINT storico_segnacollo_fk FOREIGN KEY (segnacollo) REFERENCES public.segnacollo(segnacollo);


--
-- TOC entry 5090 (class 2606 OID 43095)
-- Name: storico storico_stato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico
    ADD CONSTRAINT storico_stato_fk FOREIGN KEY (id_stato) REFERENCES public.stato(id);


--
-- TOC entry 5091 (class 2606 OID 43100)
-- Name: veicolo veicolo_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.veicolo
    ADD CONSTRAINT veicolo_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5093 (class 2606 OID 43105)
-- Name: veicolo_lavoro veicolo_lavoro_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.veicolo_lavoro
    ADD CONSTRAINT veicolo_lavoro_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5094 (class 2606 OID 43110)
-- Name: veicolo_lavoro veicolo_lavoro_veicolo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.veicolo_lavoro
    ADD CONSTRAINT veicolo_lavoro_veicolo_fk FOREIGN KEY (targa) REFERENCES public.veicolo(targa);


--
-- TOC entry 5092 (class 2606 OID 43115)
-- Name: veicolo veicolo_sede_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.veicolo
    ADD CONSTRAINT veicolo_sede_fk FOREIGN KEY (sede) REFERENCES public.sede(id);


--
-- TOC entry 5288 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;


-- Completed on 2026-04-01 15:00:00

--
-- PostgreSQL database dump complete
--

\unrestrict U378H8HcHVINg2AUEuBbBKfWb9FQNjJpLKaUtwtNBKmE8YBqy8M5aI78KyKvZAY

