package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sparsh23.laltern.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Buyer_Policies.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Buyer_Policies#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Buyer_Policies extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView buyer_policy;

    private OnFragmentInteractionListener mListener;

    public Buyer_Policies() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Buyer_Policies.
     */
    // TODO: Rename and change types and number of parameters
    public static Buyer_Policies newInstance(String param1) {
        Buyer_Policies fragment = new Buyer_Policies();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_buyer__policies,container,false);
        buyer_policy = (TextView)root.findViewById(R.id.buyers_policy_text);

        buyer_policy.setMovementMethod(LinkMovementMethod.getInstance());



        if (mParam1.equals("buyer"))
        {
            buyer_policy.setText(Html.fromHtml("<p><strong>Lal10 Service Pledge</strong></p>\n" +
                    "<p dir=\"ltr\">We're committed to creating India's best sourcing marketplace of Handicraft products made by artisans at real time. That's why we've enhanced our customer service pledge to make it easier and more secure than ever to buy on Lal10.</p>\n" +
                    "<p dir=\"ltr\"><strong>Escrow Protection, On Every Order</strong></p>\n" +
                    "<p dir=\"ltr\">Your money is held safely by Lal10 until you confirm successful delivery of your order. You can relax knowing your artisan (seller) will not be paid until you are satisfied with your order.</p>\n" +
                    "<p dir=\"ltr\"><strong>Guaranteed Secure Payments</strong></p>\n" +
                    "<p dir=\"ltr\">You payment information is provided directly to Lal10 (not to your seller). We also use industry leading data encryption and bank-level security to ensure your payment details are safe, protected, and out of harm's way from intruders.</p>\n" +
                    "<p dir=\"ltr\"><strong>Faster Dispute Processing and Resolution</strong></p>\n" +
                    "<p dir=\"ltr\">Buyers can now open a dispute request with your seller within as few as 4 days of your order being dispatched. Lal10 will also more quickly and proactively mediate your discussion to bring it to a swift conclusion.</p>\n" +
                    "<p dir=\"ltr\"><strong>100% Guaranteed Returns</strong></p>\n" +
                    "<p dir=\"ltr\">Available on orders that don't arrive, arrive damaged, or contain products that differ significantly from what your seller advertised.</p>\n" +
                    "<p dir=\"ltr\"><strong>Easy Refund and Dispute Policy</strong></p>\n" +
                    "<p dir=\"ltr\">Not 100% satisfied with your purchase? Don't worry. You have the option to submit a dispute and get up to a full refund. Because your payment is under escrow, your seller doesn't receive money until you are satisfied with your order.</p>\n" +
                    "<p dir=\"ltr\"><strong>Get Refunded After Releasing Payment</strong></p>\n" +
                    "<p dir=\"ltr\">If you've already released payment to your seller and still find yourself unsatisfied with your product, we now let you to open a refund request anytime up to 30 days after payment release.</p>"));



        }
        else if(mParam1.equals("payment"))
        {
            buyer_policy.setText(Html.fromHtml("<p><strong>How do I make a payment for a lal10 purchase?</strong></p>\n" +
                    "<p>Lal10 offers multiple methods to make payments for your order: Credit Card, Debit Card and Net Banking. You can also hold your amount in Escrow.&nbsp; International payments can be received via PayPal. All Credit/Debit card details remain private and confidential. Lal10 and our trusted payment gateway Razorpay use SSL encryption technology to protect your card information.</p>\n" +
                    "<p>If you have any problem with our Payment gateway, please write to us at&nbsp;<a href=\"mailto:maneet@lal10.com\" target=\"_blank\">maneet@lal10.com</a></p>\n" +
                    "<p><strong>Are there any hidden charges on&nbsp;<a href=\"http://lal10.com/\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=en&amp;q=http://lal10.com&amp;source=gmail&amp;ust=1482373000454000&amp;usg=AFQjCNFyi3M2gB0-Ebxtm7hq46YhcexeRg\">lal10.com</a>&nbsp;(Octroi, Sales Tax)?</strong></p>\n" +
                    "<p>There are absolutely no hidden charges when you make a purchase with Lal10.&nbsp; The prices you see on our product pages are exactly what you pay for the item.</p>\n" +
                    "<p><strong>How do I pay using a Credit/Debit card and is it safe?</strong></p>\n" +
                    "<p>We accept VISA and MasterCard credit/debit cards - Customers will need to key in their 16-digit Credit/Debit Card number and the 3 digit CVV Code (Card Verification Value - found on the back of the card) and complete the payment flow.</p>\n" +
                    "<p>All Credit/Debit card details remain private and confidential.&nbsp;<a href=\"http://lal10.com/\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=en&amp;q=http://lal10.com&amp;source=gmail&amp;ust=1482373000454000&amp;usg=AFQjCNFyi3M2gB0-Ebxtm7hq46YhcexeRg\">lal10.com</a>&nbsp;and our trusted payment gateways use SSL encryption technology to protect your card information.</p>\n" +
                    "<p><strong>Debit Cards</strong></p>\n" +
                    "<p>We accept Debit Cards issued by the following banks:</p>\n" +
                    "<ul>\n" +
                    "    <li>AXIS Bank Debit Card (VISA Electron only)</li>\n" +
                    "    <li>Canara Bank Debit Card</li>\n" +
                    "    <li>Citibank Debit Card</li>\n" +
                    "    <li>Corporation Bank Debit Card (VISA Electron only)</li>\n" +
                    "    <li>Deutsche Bank Debit Card (VISA Electron only)</li>\n" +
                    "    <li>HDFC Bank Debit Card (VISA Electron only)</li>\n" +
                    "    <li>ICICI Bank Debit Card</li>\n" +
                    "    <li>Indian Overseas Bank Debit Card (VISA Electron only)</li>\n" +
                    "    <li>ING Vyasa Bank Debit Card (VISA Electron only)</li>\n" +
                    "    <li>Karur Vysya Bank Debit Card (VISA Electron only)</li>\n" +
                    "    <li>Punjab National Bank Debit Card</li>\n" +
                    "</ul>\n" +
                    "<p><strong>Internet Banking</strong></p>\n" +
                    "<p>We accept payment through Internet Banking for the following banks:</p>\n" +
                    "<ul>\n" +
                    "    <li>ABN AMRO Bank</li>\n" +
                    "    <li>AXIS Bank</li>\n" +
                    "    <li>Allahabad Bank</li>\n" +
                    "    <li>Andhra Bank</li>\n" +
                    "    <li>Bank of Bahrain and Kuwait</li>\n" +
                    "    <li>Bank of Baroda Corp Account</li>\n" +
                    "    <li>Bank of Baroda Retail Account</li>\n" +
                    "    <li>Bank of India</li>\n" +
                    "    <li>Bank of Maharashtra</li>\n" +
                    "    <li>Bank of Rajasthan</li>\n" +
                    "    <li>Citibank</li>\n" +
                    "    <li>City Union Bank</li>\n" +
                    "    <li>Corporation Bank</li>\n" +
                    "    <li>Deutsche Bank</li>\n" +
                    "    <li>Dhanlaxmi Bank</li>\n" +
                    "    <li>Federal Bank</li>\n" +
                    "    <li>HDFC Bank</li>\n" +
                    "    <li>ICICI Bank</li>\n" +
                    "    <li>IDBI Bank</li>\n" +
                    "    <li>ING Vysya Bank</li>\n" +
                    "    <li>IOB Debit Card</li>\n" +
                    "    <li>Indian Overseas Bank</li>\n" +
                    "    <li>Indian Bank</li>\n" +
                    "    <li>IndusInd Bank</li>\n" +
                    "    <li>Jammu &amp; Kashmir Bank</li>\n" +
                    "    <li>Karnataka Bank</li>\n" +
                    "    <li>Karur Vysya Net Bank</li>\n" +
                    "    <li>Kotak Mahindra Bank</li>\n" +
                    "    <li>Lakshmi Vilas Bank</li>\n" +
                    "    <li>Oriental Bank Of Commerce</li>\n" +
                    "    <li>Punjab National Bank Corp Account</li>\n" +
                    "    <li>Punjab National Bank Retail Account</li>\n" +
                    "    <li>Royal Bank of Scotland</li>\n" +
                    "    <li>South Indian Bank</li>\n" +
                    "    <li>Standard Chartered Bank</li>\n" +
                    "    <li>State Bank of Hyderabad</li>\n" +
                    "    <li>State Bank Of India</li>\n" +
                    "    <li>State Bank of Mysore</li>\n" +
                    "    <li>State Bank of Travancore</li>\n" +
                    "    <li>Syndicate Bank</li>\n" +
                    "    <li>Tamilnad Mercantile Bank</li>\n" +
                    "    <li>Union Bank Of India</li>\n" +
                    "    <li>Vijaya Bank</li>\n" +
                    "    <li>YES BANK</li>\n" +
                    "</ul>\n" +
                    "<p>&nbsp;and many more updated regularly.</p>\n" +
                    "<p>To know more, drop a mail at&nbsp;<strong><a href=\"mailto:sanchit@lal10.com\" target=\"_blank\">sanchit@lal10.com</a></strong></p>"));

        }
        else if (mParam1.equals("shipping"))
        {
            buyer_policy.setText(Html.fromHtml("<p><strong>Delivery/shipping charges?</strong></p>\n" +
                    "<p>Lal10 provides FREE SHIPPING ALL OVER INDIA for all bulk orders. We cover almost all the places across the length and breadth of the country.</p>\n" +
                    "<p>If you require any assistance in understanding our shipping policy please give us a ring on 09167122150 or write to us at&nbsp;<a href=\"mailto:sanchit@lal10.com\" target=\"_blank\">sanchit@lal10.com</a>&nbsp;.</p>\n" +
                    "<p>All our shipments are delivered through FedEx, DHL, Bluedart, Ecom Express and Delhivery.</p>\n" +
                    "<p><strong>Delivery timeline? When will my products be delivered after i have placed my order?</strong></p>\n" +
                    "<p>2 days for the metros and a maximum of 5 working days for remote locations from the day the product is ready from the artisans end. Customized orders will take time as decided by the artisan. When your order is dispatched, you'll receive a confirmation mail with tracking details.</p>\n" +
                    "<p><strong>Minimum order value?</strong></p>\n" +
                    "<p>None!&nbsp; As big and as small as you want. Over here, size doesn&rsquo;t matter.</p>\n" +
                    "<p><strong>Who are your logistics partners?</strong></p>\n" +
                    "<p>Our logistics partners are FedEx, DHL, Bluedart, Ecom Express and Delivery.</p>\n" +
                    "<p><strong>Warranty and hidden costs (sales tax, octroi etc.)?</strong></p>\n" +
                    "<p>NONE. Price mentioned on the website is the final price. What you see is what you pay. Our prices are inclusive of all taxes</p>\n" +
                    "<p><strong>Can I change my shipping address after I have placed an order?</strong></p>\n" +
                    "<p>If your order is still being processed, and has not been dispatched, you can contact us via Email (<a href=\"mailto:sanchit@lal10.com\" target=\"_blank\">sanchit@lal10.com</a>) or Phone and modify your shipping address after providing your order details confirmation.</p>\n" +
                    "<p><strong>International deliveries?</strong></p>\n" +
                    "<p>Yes, definitely. We have got our Jets ready so that we can reach you anywhere on the globe. Someday we might deliver in mars too, coz we have an ISRO guy in our team.</p>"));

        }

        else if (mParam1.equals("bulk"))
        {
           buyer_policy.setText(Html.fromHtml("<p>Do you find our products irresistible and are hungry for more?\n" +
                   "</p>\n" +
                   "<p>Is your organisation happy to form a relationship with Lal10?&nbsp;\n" +
                   "</p>\n" +
                   "<p>If yes, we do provide attractive discounts for bulk/corporate orders for Niche and Authentic Indian Handicrafts straight from the artisans.&nbsp;\n" +
                   "</p>\n" +
                   "<p>For Bulk Order Queries &amp; Corporate orders,\n" +
                   "</p>\n" +
                   "<p>Please write to us with your selection to&nbsp;<strong><a href=\"mailto:maneet@lal10.com\" target=\"_blank\">maneet@lal10.com</a>&nbsp;</strong>or call us at&nbsp;<strong>09167106930</strong>\n" +
                   "</p>"));
        }


        return root;
    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
